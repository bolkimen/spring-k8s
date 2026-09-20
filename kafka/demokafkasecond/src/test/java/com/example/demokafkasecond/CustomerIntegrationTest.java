package com.example.demokafkasecond;

import com.example.demokafkasecond.model.Customer;
import org.apache.kafka.streams.KafkaStreams;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        topics = "${spring.kafka.customers-table-topic-name}",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class CustomerIntegrationTest {

    @Value(value = "${spring.kafka.customers-topic-name}")
    private String customerTopicName;

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    private KafkaTemplate<String, Customer> customerKafkaTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Environment environment;

    @Test
    void printKafkaBootstrapServer() {
        System.out.println(
                "BOOTSTRAP = " +
                        environment.getProperty("spring.kafka.bootstrap-servers")
        );
    }

    @Test
    void printMappings() throws Exception {

        awaitKafkaStreamsRunning();

        MockMvc mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/customer/123")
                )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReadCustomerFromKTableThroughRestApiV2() {

        Customer customer = new Customer(
                UUID.randomUUID().toString(),
                "John",
                "john@example.com"
        );

        customerKafkaTemplate.send(
                customerTopicName,
                customer.getId(),
                customer
        );

        awaitKafkaStreamsRunning();

        Awaitility.await()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofMillis(200))
                .untilAsserted(() -> {

                    ResponseEntity<Customer> response =
                            restTemplate.getForEntity(
                                    "/api/customer/" + customer.getId(),
                                    Customer.class
                            );

                    assertThat(response.getStatusCode())
                            .isEqualTo(HttpStatus.OK);

                    Customer result = response.getBody();

                    assertThat(result).isNotNull();
                    assertThat(result.getId())
                            .isEqualTo(customer.getId());
                    assertThat(result.getName())
                            .isEqualTo(customer.getName());
                    assertThat(result.getEmail())
                            .isEqualTo("john@example.com");
                });
    }

    @Test
    void shouldReadCustomerFromKTableThroughRestApi()
            throws Exception {

        // Given
        Customer customer = new Customer(
                UUID.randomUUID().toString(),
                "John",
                "john@example.com"
        );

        customerKafkaTemplate.send(
                customerTopicName,
                customer.getId(),
                customer
        ).get();

        awaitKafkaStreamsRunning();

        // When
        ResponseEntity<Customer> response =
                awaitCustomer(customer.getId());

        // Then
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().getId())
                .isEqualTo(customer.getId());

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

                    ResponseEntity<List> allCustomersResponse =
                            restTemplate.getForEntity(
                                    "/api/customer",
                                    List.class
                            );

                    assertThat(allCustomersResponse.getStatusCode())
                            .isEqualTo(HttpStatus.OK);

                    ResponseEntity<Customer> response =
                            restTemplate.getForEntity(
                                    "/api/customer/" + id,
                                    Customer.class
                            );

                    assertThat(response.getStatusCode())
                            .isEqualTo(HttpStatus.OK);
                });

        return restTemplate.getForEntity(
                "/api/customer/" + id,
                Customer.class
        );
    }

    private void awaitKafkaStreamsRunning() {
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .pollInterval(Duration.ofMillis(200))
                .until(() -> {
                    KafkaStreams kafkaStreams =
                            streamsBuilderFactoryBean.getKafkaStreams();

                    return kafkaStreams != null
                            && kafkaStreams.state() == KafkaStreams.State.RUNNING;
                });
    }
}
