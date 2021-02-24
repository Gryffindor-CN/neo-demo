package neo.demo.springbatch.sample.batch;


import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-19 01:19
 */
public class TestItemWriter<T> implements ItemWriter<T> {
    @Override
    public void write(List<? extends T> items) throws Exception {
        items.stream().forEach(a->{
            Article article=(Article)a;
            System.err.println(article.getTitle());
        });
    }
}
