package com.skyforce.goal.application;

import com.google.gson.Gson;
import com.skyforce.goal.util.blockio.BlockioWebsocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySources(value = {@PropertySource("classpath:application.properties")})
@ComponentScan("com.skyforce.goal")
@EnableJpaRepositories(basePackages = "com.skyforce.goal.repository")
@EntityScan(basePackages = "com.skyforce.goal.model")
public class App {

    private static BlockioWebsocket blockioWebsocket;

    public static void main(String[] args) {
        // TODO Enable on bitcoin completion
        //blockioWebsocket = new BlockioWebsocket();
        //blockioWebsocket.connect();
        SpringApplication.run(App.class, args);
    }

    @Bean
    @Scope("singleton")
    public BlockioWebsocket blockioWebsocket() {
        return blockioWebsocket;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
