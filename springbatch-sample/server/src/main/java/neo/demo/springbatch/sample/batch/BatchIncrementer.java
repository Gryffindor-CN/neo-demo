package neo.demo.springbatch.sample.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Date;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-18 23:09
 */
public class BatchIncrementer implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        return new JobParametersBuilder()
                .addLong("batchDate",new Date().getTime())
                .toJobParameters();
    }
}
