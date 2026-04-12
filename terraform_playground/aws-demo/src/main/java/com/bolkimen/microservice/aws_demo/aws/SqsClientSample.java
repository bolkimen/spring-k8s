package com.bolkimen.microservice.aws_demo.aws;

import com.bolkimen.microservice.aws_demo.aws.dynamodb.Files;
import com.bolkimen.microservice.aws_demo.aws.dynamodb.Payment;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class SqsClientSample {
    @Autowired
    private SqsTemplate sqsTemplate;

    public void sendDynamoMessage() {
    }
}
