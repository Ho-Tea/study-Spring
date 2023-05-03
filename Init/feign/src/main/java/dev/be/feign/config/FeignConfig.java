package dev.be.feign.config;

import dev.be.feign.service.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Member member(){
        return new Member();
    }
}
