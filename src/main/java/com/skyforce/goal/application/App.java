package com.skyforce.goal.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.skyforce.goal.controller")
@ComponentScan("com.skyforce.goal")
@EnableJpaRepositories(basePackages = "com.skyforce.goal.repository")
@EntityScan(basePackages = "com.skyforce.goal.model")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
