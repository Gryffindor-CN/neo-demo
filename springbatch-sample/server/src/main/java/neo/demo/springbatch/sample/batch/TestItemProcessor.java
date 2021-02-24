package neo.demo.springbatch.sample.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-19 00:43
 */
public class TestItemProcessor implements ItemProcessor<Article, Article> {

    private static final Logger log = LoggerFactory.getLogger(TestItemProcessor.class);

    @Override
    public Article process(Article article) throws Exception {
        log.info("【{}】经过处理器",article.getTitle());
        return article;
    }
}
