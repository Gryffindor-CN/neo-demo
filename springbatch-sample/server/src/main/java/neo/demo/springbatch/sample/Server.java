package neo.demo.springbatch.sample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: CP.Chen
 * @Date: 2021-02-10 22:06
 */
@SpringBootApplication
@EnableBatchProcessing
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}
