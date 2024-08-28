package com.fpm.userservice.aws.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fpm.userservice.aws.model.UserRegistraionModel;
import com.fpm.userservice.aws.service.SessionService;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.db.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UserRegistrationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionService sessionService;

    @Override
    @Transactional
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        String body = input.getBody();
        if (body == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("{\"success\":false, \"message\":\"Invalid input\"}");
        }

        UserRegistraionModel.RegistrationRequest registrationRequest = parseRequestBody(body);
        if (registrationRequest == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("{\"success\":false, \"message\":\"Invalid request body\"}");
        }

        if (userRepository.findByUsername(registrationRequest.username()).isPresent()) {
            return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody("{\"success\":false, \"message\":\"User already exists\"}");
        }

        User user = new User();
        user.setUsername(registrationRequest.username());
        user.setPasswordHash(registrationRequest.passwordHash());
        userRepository.save(user);

        String sessionId = UUID.randomUUID().toString();
        sessionService.saveSession(sessionId, registrationRequest.username());

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("{\"success\":true, \"message\":\"User registered successfully\"}");
    }

    private UserRegistraionModel.RegistrationRequest parseRequestBody(String body) {
        // Implement your JSON parsing logic here (e.g., using Jackson or Gson)
        // Return a new RegistrationRequest or null if parsing fails
        return new UserRegistraionModel.RegistrationRequest("testUser", "testHash"); // Dummy values for illustration
    }
}
