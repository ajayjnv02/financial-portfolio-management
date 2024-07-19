package com.fpm.userservice.aws.lambda.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fpm.userservice.db.entity.User;
import com.google.gson.Gson;

import java.util.UUID;

public class UserRegistrationHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = System.getenv("USERS_TABLE");

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        this.initDynamoDbClient();
        User user = new Gson().fromJson(request.getBody(), User.class);

        PutItemOutcome outcome = this.saveUser(user);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(201);
        response.setBody(outcome.toString());
        return response;
    }

    private PutItemOutcome saveUser(User user) {
        Table table = dynamoDb.getTable(DYNAMODB_TABLE_NAME);
        return table.putItem(new Item()
                .withPrimaryKey("id", UUID.randomUUID().toString())
                .withString("username", user.getUserName())
                .withString("email", user.getEmail())
                .withString("password", user.getPassword())
                .withString("createdAt", String.valueOf(user.getCreatedAt())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDb = new DynamoDB(client);
    }
}