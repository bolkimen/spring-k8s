package com.bolkimen.microservice.aws_demo.controller;

import com.bolkimen.microservice.aws_demo.aws.S3ClientSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class AwsDemoController {
    @Autowired
    S3ClientSample s3ClientSample;

    @GetMapping("/")
    public String message() throws IOException {
        s3ClientSample.readFile();
        return "message";
    }
}
