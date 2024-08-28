package com.fpm.userservice.aws.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpm.userservice.aws.model.UserLoginModel;
import com.fpm.userservice.aws.service.SessionService;
import com.fpm.userservice.db.entity.User;
import com.fpm.userservice.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserLoginHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final SessionService sessionService;

    @Autowired
    public UserLoginHandler(UserRepository userRepository, ObjectMapper objectMapper, SessionService sessionService) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;

    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {

            UserLoginModel.LoginRequest request = objectMapper.readValue(input.getBody(), UserLoginModel.LoginRequest.class);
            String sessionId = generateSessionId();
            boolean isValid = validateUser(request.username(), request.passwordHash());
            UserLoginModel.LoginResponse response = new UserLoginModel.LoginResponse(
                    isValid,
                    isValid ? "Login successful" : "Invalid username or password",
                    isValid ? sessionId : null
            );
            if (isValid) {
                sessionService.saveSession(sessionId, request.username());
            }


            int statusCode = isValid ? 200 : 401;
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(statusCode)
                    .withBody(objectMapper.writeValueAsString(response));

        } catch (Exception e) {

            context.getLogger().log("Error: " + e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("Internal server error");
        }
    }

    private String generateSessionId() {
        // Implement session ID generation logic (e.g., UUID)
        return java.util.UUID.randomUUID().toString();
    }

    private boolean validateUser(String username, String passwordHash) {
        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordHash.equals(user.getPasswordHash());
        }
        return false;
    }
}
