package com.example.bookhub;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableCaching
public class BookHubApplication {

    public static void main(String[] args) {

        System.out.println("hello world");
        SpringApplication.run(BookHubApplication.class, args);

    }

}
