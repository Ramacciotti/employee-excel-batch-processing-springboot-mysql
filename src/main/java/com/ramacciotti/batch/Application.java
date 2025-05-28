package com.ramacciotti.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ramacciotti.batch")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
