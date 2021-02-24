package neo.demo.springbatch.sample.batch;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-18 19:31
 */
@Configuration
@ConfigurationProperties(prefix = "partition.rabbit")
public class PartitionBatchConfig {

    private static final int GRID_SIZE = 5;
    private static final String SLAVE = "slaveStep";

    private String host;
    private Integer port = 5672;
    private String username;
    private String password;
    private String virtualHost;
    private String queueName = "partition.requests";
    private int connRecvThreads = 5;
    private int channelCacheSize = 10;


    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    ApplicationContext applicationContext;

    /*************************************************************************
     *                                 Master                                *
     *************************************************************************/
    @Profile({"master", "mixed"})
    @Bean
    public Job job(Step masterStep) {
        return jobBuilderFactory.get("buildLabelJob")
                .start(masterStep)
                .incrementer(new BatchIncrementer())
                .listener(new JobListener())
                .build();
    }

    @Profile({"master", "mixed"})
    @Bean
    public Step masterStep(PartitionHandler partitionHandler,
                           DataSource dataSource,
                           Step slaveStep) {
        return stepBuilderFactory.get("masterStep.manager")
                .partitioner(SLAVE, new TestPartitioner(dataSource))
                .step(slaveStep)
                .partitionHandler(partitionHandler)
                .build();
    }

    /*************************************************************************
     *                                  Slave                                *
     *************************************************************************/
    @Bean
    @Profile({"slave", "mixed"})
    @ServiceActivator(inputChannel = "inboundRequests", outputChannel = "outboundStaging")
    public StepExecutionRequestHandler stepExecutionRequestHandler() {
        StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();
        BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
        stepLocator.setBeanFactory(this.applicationContext);
        stepExecutionRequestHandler.setStepLocator(stepLocator);
        stepExecutionRequestHandler.setJobExplorer(this.jobExplorer);
        return stepExecutionRequestHandler;
    }

    @Bean
    public Step slaveStep(TestItemProcessor testItemProcessor,
                          JpaPagingItemReader reader) {
        CompositeItemProcessor itemProcessor = new CompositeItemProcessor();
        List<ItemProcessor> processors = new ArrayList<>();
        processors.add(testItemProcessor);
        itemProcessor.setDelegates(processors);
        return stepBuilderFactory.get(SLAVE)
                .<Article, Article>chunk(1000)
                .reader(reader)
                .processor(itemProcessor)
                .writer(new TestItemWriter())
                .build();
    }

    @Bean
    public TestItemProcessor testItemProcessor() {
        return new TestItemProcessor();
    }

    /*************************************************************************
     *                               Partition                               *
     *************************************************************************/
    @Profile({"master", "mixed"})
    @JobScope
    @Bean
    public PartitionHandler partitionHandler(MessagingTemplate messagingTemplate,
                                             @Value("#{jobParameters['gridSize']}") Integer gridSize) throws Exception {
        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
        partitionHandler.setStepName(SLAVE);
        partitionHandler.setGridSize(gridSize == null ? GRID_SIZE : gridSize);
        partitionHandler.setMessagingOperations(messagingTemplate);
        partitionHandler.setPollInterval(5000l);
        partitionHandler.setJobExplorer(jobExplorer);
        partitionHandler.afterPropertiesSet();
        return partitionHandler;
    }

    @StepScope
    @Bean(destroyMethod = "")
    public JpaPagingItemReader<Article> reader(
            @Value("#{stepExecutionContext['minValue']}") Long minValue,
            @Value("#{stepExecutionContext['maxValue']}") Long maxValue) {
        System.err.println("接收到分片参数["+minValue+"->"+maxValue+"]");
        JpaPagingItemReader<Article> reader = new JpaPagingItemReader<>();
        JpaNativeQueryProvider queryProvider = new JpaNativeQueryProvider();
        String sql = "select * from article where arcid >= :minValue and arcid <= :maxValue";
        queryProvider.setSqlQuery(sql);
        queryProvider.setEntityClass(Article.class);
        reader.setQueryProvider(queryProvider);
        Map queryParams = new HashMap();
        queryParams.put("minValue", minValue);
        queryParams.put("maxValue", maxValue);
        reader.setParameterValues(queryParams);
        reader.setEntityManagerFactory(entityManagerFactory);
        return reader;
    }

    /**
     * Integration 全局轮询器
     * @return
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata pollerMetadata() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10));
        return pollerMetadata;
    }

    /*************************************************************************
     *                                RabbitMQ                               *
     *************************************************************************/
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setExecutor(taskExecutor());
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        /**
         * 默认通道缓存25,多线程环境中，较小的缓存意味着通道的创建和关闭将以很高的速率运行.加大默认缓存大小可避免这种开销
         * 如果达到了限制,调用线程将会阻塞，直到某个通道可用或者超时, 在后者的情况中，将抛出 AmqpTimeoutException异常.
         */
        connectionFactory.setChannelCacheSize(channelCacheSize);
        return connectionFactory;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(connRecvThreads);
        executor.initialize();
        return executor;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queueName);
        container.setAutoStartup(false);
        return container;
    }

    /*************************************************************************
     *                              Integration                              *
     *************************************************************************/
    @Bean
    public MessagingTemplate messagingTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate(outboundRequests());
        messagingTemplate.setReceiveTimeout(60000000l);
        return messagingTemplate;
    }

    @Bean
    public DirectChannel outboundRequests() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundRequests")
    public AmqpOutboundEndpoint amqpOutboundEndpoint(AmqpTemplate template) {
        AmqpOutboundEndpoint endpoint = new AmqpOutboundEndpoint(template);
        endpoint.setExpectReply(true);
        endpoint.setOutputChannel(inboundRequests());
        endpoint.setRoutingKey(queueName);
        return endpoint;
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(queueName, false);
    }

    @Profile({"slave", "mixed"})
    @Bean
    public AmqpInboundChannelAdapter inbound(SimpleMessageListenerContainer listenerContainer) {
        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(inboundRequests());
        adapter.afterPropertiesSet();
        return adapter;
    }

    @Bean
    public PollableChannel outboundStaging() {
        return new NullChannel();
    }

    @Bean
    public QueueChannel inboundRequests() {
        return new QueueChannel();
    }

    /*************************************************************************
     *                             getter/setter                             *
     *************************************************************************/
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getConnRecvThreads() {
        return connRecvThreads;
    }

    public void setConnRecvThreads(int connRecvThreads) {
        this.connRecvThreads = connRecvThreads;
    }

    public int getChannelCacheSize() {
        return channelCacheSize;
    }

    public void setChannelCacheSize(int channelCacheSize) {
        this.channelCacheSize = channelCacheSize;
    }
}
