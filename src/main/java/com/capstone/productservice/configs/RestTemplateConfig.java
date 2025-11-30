package com.capstone.productservice.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getLoadBalancedRestTemplate()
    {
        return new RestTemplate();
    }
}