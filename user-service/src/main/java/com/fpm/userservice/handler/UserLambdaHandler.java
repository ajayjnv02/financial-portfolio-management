package com.fpm.userservice.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fpm.userservice.UserService;
import com.fpm.userservice.model.User;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final UserService userService = new UserService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        String path = request.getPath();
        String method = request.getHttpMethod();
        String body = request.getBody();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);

        try {
            if ("/users/register".equals(path) && "POST".equalsIgnoreCase(method)) {
                return handleRegister(body, response);
            } else if ("/users/login".equals(path) && "POST".equalsIgnoreCase(method)) {
                return handleLogin(body, response);
            } else if (path.startsWith("/users/") && "GET".equalsIgnoreCase(method)) {
                return handleGetUser(request, response);
            } else if (path.startsWith("/users/") && "PUT".equalsIgnoreCase(method)) {
                return handleUpdateUser(request, response);
            } else {
                response.setStatusCode(404);
                response.setBody("{\"error\":\"Not Found\"}");
                return response;
            }
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setBody("{\"error\":\"" + e.getMessage() + "\"}");
            return response;
        }
    }

    private APIGatewayProxyResponseEvent handleRegister(String body, APIGatewayProxyResponseEvent response) {
        JSONObject json = new JSONObject(body);
        String username = json.getString("username");
        String password = json.getString("password");
        String email = json.getString("email");

        userService.registerUser(username, password, email);

        response.setStatusCode(200);
        response.setBody("{\"message\":\"Registration successful\"}");
        return response;
    }

    private APIGatewayProxyResponseEvent handleLogin(String body, APIGatewayProxyResponseEvent response) {
        JSONObject json = new JSONObject(body);
        String username = json.getString("username");
        String password = json.getString("password");

        String token = userService.loginUser(username, password);

        response.setStatusCode(200);
        response.setBody("{\"sessionToken\":\"" + token + "\"}");
        return response;
    }

    private APIGatewayProxyResponseEvent handleGetUser(APIGatewayProxyRequestEvent request, APIGatewayProxyResponseEvent response) {
        // path /users/{userId}
        String[] parts = request.getPath().split("/");
        long userId = Long.parseLong(parts[2]);

        // For a production app, you'd validate session token from headers here.

        User user = userService.getUserById(userId);

        JSONObject userJson = new JSONObject();
        userJson.put("id", user.id());
        userJson.put("username", user.username());
        userJson.put("email", user.email());

        response.setStatusCode(200);
        response.setBody(userJson.toString());
        return response;
    }

    private APIGatewayProxyResponseEvent handleUpdateUser(APIGatewayProxyRequestEvent request, APIGatewayProxyResponseEvent response) {
        // path /users/{userId}
        String[] parts = request.getPath().split("/");
        long userId = Long.parseLong(parts[2]);

        JSONObject json = new JSONObject(request.getBody());
        String newEmail = json.getString("email");

        // For production, validate session token to ensure the user is authorized.

        userService.updateUserEmail(userId, newEmail);

        response.setStatusCode(200);
        response.setBody("{\"message\":\"User updated successfully\"}");
        return response;
    }
}

