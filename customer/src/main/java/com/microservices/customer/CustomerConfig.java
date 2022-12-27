package com.microservices.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {
/*
    @Bean
    @LoadBalanced //request can be send for different applications with different ports
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/
}
