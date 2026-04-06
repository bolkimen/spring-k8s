package com.bolkimen.microservice.aws_demo.config;

import io.awspring.cloud.autoconfigure.core.AwsClientBuilderConfigurer;
import io.awspring.cloud.autoconfigure.s3.properties.S3Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
public class AwsDemoConfig {
    @Autowired
    private Environment env;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .credentialsProvider(getCredentialsProvider())
                .endpointOverride(URI.create(env.getProperty("aws.serviceEndpoint", "http://localhost:4566")))
                .region(Region.of(env.getProperty("aws.signingRegion", "us-east-1")))
                .forcePathStyle(true)
                .build();
    }

    private StaticCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(
                env.getProperty("aws.accessKey", "none"),
                env.getProperty("aws.secretKey", "none")));
    }
}
