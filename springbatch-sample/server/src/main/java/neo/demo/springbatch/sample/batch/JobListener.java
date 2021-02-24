package neo.demo.springbatch.sample.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-18 23:12
 */
public class JobListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.error("Job准备执行,JobName:{}",jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.error("EndOfDayJob执行完毕,执行结果:{}",jobExecution.getExitStatus());
    }
}
