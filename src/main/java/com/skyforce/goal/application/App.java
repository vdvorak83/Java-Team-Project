package com.skyforce.goal.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("goal.skyforce.goal")
@EnableJpaRepositories(basePackages = "com.skyforce.goal.repository")
@EntityScan(basePackages = "com.skyforce.goal.model")
// Should be deleted after configuring Spring Security.
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
