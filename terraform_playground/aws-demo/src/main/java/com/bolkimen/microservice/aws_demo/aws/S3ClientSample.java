package com.bolkimen.microservice.aws_demo.aws;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class S3ClientSample {
    private static final String BUCKET_NAME = "my-test-bucket";

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Template s3Template;

    public void readFile() throws IOException {
        // uploading file with metadata
        s3Template.upload(BUCKET_NAME, "file.txt", createInputStream("some text"), ObjectMetadata.builder().contentType("text/plain").build());
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                request -> request.bucket(BUCKET_NAME).key("file.txt"));

        String fileContent = StreamUtils.copyToString(response, StandardCharsets.UTF_8);
        System.out.println(fileContent);
    }

    private InputStream createInputStream(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
