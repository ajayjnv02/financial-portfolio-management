package com.fpm.userservice.db.repository;


import com.fpm.userservice.db.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class UserRepository {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    private DynamoDbTable<User> userTable;

    @PostConstruct
    public void init() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.userTable = enhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    public void save(User user) {
        userTable.putItem(PutItemEnhancedRequest.builder(User.class).item(user).build());
    }

    public User findByUsername(String username) {
        return userTable.getItem(r -> r.key(k -> k.partitionValue(username)));
    }
}
