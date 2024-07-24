package com.fpm.userservice.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpm.userservice.config.DynamoDbConfig;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.db.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;


public class UserRegistrationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ApplicationContext context;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserRegistrationHandler() {
        context = new AnnotationConfigApplicationContext(DynamoDbConfig.class);
        userRepository = context.getBean(UserRepository.class);
        objectMapper = new ObjectMapper();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            User user = objectMapper.readValue(request.getBody(), User.class);
            userRepository.save(user);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            response.setStatusCode(200);
            response.setBody("{\"message\": \"User registered successfully!\"}");
            response.setHeaders(headers);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("{\"message\": \"Error registering user.\"}");
        }
        return response;
    }
    }

