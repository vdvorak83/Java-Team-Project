package com.skyforce.goal.application;

import com.google.gson.Gson;
import com.skyforce.goal.service.MoneySyncService;
import com.skyforce.goal.util.GoalStatusUtil;
import com.skyforce.goal.util.blockio.BlockioWebsocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySources(value = {@PropertySource("classpath:application.properties")})
@ComponentScan("com.skyforce.goal")
@EnableJpaRepositories(basePackages = "com.skyforce.goal.repository")
@EntityScan(basePackages = "com.skyforce.goal.model")
public class App {

    private static BlockioWebsocket blockioWebsocket;
    private static GoalStatusUtil goalStatusUtil;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        //Todo
        blockioWebsocket = context.getBean(BlockioWebsocket.class);
        blockioWebsocket.connect();
        MoneySyncService moneySyncService = context.getBean(MoneySyncService.class);
        moneySyncService.updateTransactions();
        GoalStatusUtil goalStatusUtil = context.getBean(GoalStatusUtil.class);
        goalStatusUtil.update();
        goalStatusUtil.enableRegulularUpdate();
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
