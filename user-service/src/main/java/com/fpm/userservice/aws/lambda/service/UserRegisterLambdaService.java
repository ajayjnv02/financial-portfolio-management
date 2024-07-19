package com.fpm.userservice.aws.lambda.service;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fpm.userservice.db.entity.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterLambdaService {
    private final AWSLambda awsLambda;

    @Autowired
    public UserRegisterLambdaService(AWSLambda awsLambda) {
        this.awsLambda = awsLambda;
    }

    public String invokeRegisterUserLambda(User user) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName("UserRegistrationFunction")
                .withPayload(new Gson().toJson(user));

        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        return new String(invokeResult.getPayload().array());
    }
}
