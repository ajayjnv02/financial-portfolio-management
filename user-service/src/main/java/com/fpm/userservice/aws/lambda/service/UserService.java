package com.fpm.userservice.aws.lambda.service;

import com.fpm.userservice.db.entity.User;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final DynamoDbClient dynamoDbClient;

    public UserService() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public void registerUser(User user) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("username", AttributeValue.builder().s(user.getUsername()).build());
        item.put("email", AttributeValue.builder().s(user.getEmail()).build());
        item.put("password", AttributeValue.builder().s(user.getPassword()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("Users")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }
}

