package com.example.crawlssi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class CrawlSsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlSsiApplication.class, args);
    }

}
