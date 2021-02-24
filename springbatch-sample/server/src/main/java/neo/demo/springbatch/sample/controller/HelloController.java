package neo.demo.springbatch.sample.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-10 22:40
 */
@RestController
public class HelloController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importJob;

    @RequestMapping("/hello")
    public String hello(@RequestParam(name = "grid_size") Long gridSize) {
        JobParameters parameters = new JobParametersBuilder()
                .addLong("run.id",System.currentTimeMillis())
                .addLong("gridSize", gridSize)
                .toJobParameters();
        try {
            jobLauncher.run(importJob, parameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return "hello world.";
    }

}
