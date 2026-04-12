package com.bolkimen.microservice.aws_demo.aws.dynamodb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Files {
    private String fileName;

    public Files() {
    }

    @JsonCreator
    public Files(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Files files = objectMapper.readValue(json, Files.class);
        this.fileName = files.fileName;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
