package com.bolkimen.microservice.aws_demo.aws;

import com.bolkimen.microservice.aws_demo.aws.dynamodb.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class DynamoDbSample {
    @Autowired
    private DynamoDbClient dynamoDbClient;

    public void readFiles() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();

        DynamoDbTable table = enhancedClient.table("Files", TableSchema.fromBean(Files.class));
        PageIterable<Files> filesList = table.scan();
        filesList.items().forEach(files -> System.out.println("DynamoDB: " + files.getFileName()));
    }
}
