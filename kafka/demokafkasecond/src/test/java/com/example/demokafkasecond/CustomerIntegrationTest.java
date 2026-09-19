package com.example.demokafkasecond;

import com.example.demokafkasecond.model.Customer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        topics = "customers"
)
public class CustomerIntegrationTest {
    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReadCustomerFromKTableThroughRestApi()
            throws Exception {

        // Given
        Customer customer = new Customer(
                "123",
                "John",
                "john@example.com"
        );

        kafkaTemplate.send(
                "customers",
                "123",
                customer
        ).get();

        // When
        ResponseEntity<Customer> response =
                awaitCustomer("123");

        // Then
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().getId())
                .isEqualTo("123");

        assertThat(response.getBody().getName())
                .isEqualTo("John");

        assertThat(response.getBody().getEmail())
                .isEqualTo("john@example.com");
    }

    private ResponseEntity<Customer> awaitCustomer(String id)
            throws InterruptedException {

        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(200))
                .untilAsserted(() -> {

                    ResponseEntity<Customer> response =
                            restTemplate.getForEntity(
                                    "/api/customers/" + id,
                                    Customer.class
                            );

                    assertThat(response.getStatusCode())
                            .isEqualTo(HttpStatus.OK);
                });

        return restTemplate.getForEntity(
                "/api/customers/" + id,
                Customer.class
        );
    }
}
