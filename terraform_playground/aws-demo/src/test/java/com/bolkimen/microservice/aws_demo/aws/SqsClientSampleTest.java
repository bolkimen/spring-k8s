package com.bolkimen.microservice.aws_demo.aws;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

//@SpringBootTest
public class SqsClientSampleTest {
    /*@Autowired
    private AmazonSQS amazonSQS;

    @Test
    public void testSendMessage() {
        String queueUrl = amazonSQS.createQueue("test-queue").getQueueUrl();
        amazonSQS.sendMessage(queueUrl, "Hello from LocalStack!");
        List<Message> messages = amazonSQS.receiveMessage(queueUrl).getMessages();
        assertFalse(messages.isEmpty());
        assertEquals("Hello from LocalStack!", messages.get(0).getBody());
    }*/
}
