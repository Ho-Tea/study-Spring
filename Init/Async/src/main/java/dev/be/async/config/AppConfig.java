package dev.be.async.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AppConfig {

    @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown") //Create Thread Pool
    public ThreadPoolTaskExecutor defaultTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        return executor;
    }

    @Bean(name = "messagingTaskExecutor", destroyMethod = "shutdown")   //Create Thread Pool, Thread Pool이 생성이 안되는 경우를 사전에 방지
    public ThreadPoolTaskExecutor messagingTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        return executor;
    }
}
