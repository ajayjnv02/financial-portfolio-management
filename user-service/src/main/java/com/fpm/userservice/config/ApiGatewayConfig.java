package com.fpm.userservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.apigateway.ApiGatewayClient;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public ApiGatewayClient apiGatewayClient() {
        return ApiGatewayClient.builder().build();
    }
}
