package com.gooddata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point into Spring Boot Application.
 */
@SpringBootApplication
@EnableJpaRepositories("com.gooddata.dao")
public class SentenceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentenceApiApplication.class, args);
    }

}
