package com.example.demokafkasecond.controller;

import com.example.demokafkasecond.model.Customer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Value(value = "${spring.kafka.customers-table-topic-name}")
    private String customerTableTopicName;

    //@Autowired
    private KafkaStreams kafkaStreams = null;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable String id) {

        ReadOnlyKeyValueStore<String, Customer> store =
                kafkaStreams.store(
                        StoreQueryParameters.fromNameAndType(
                                customerTableTopicName,
                                QueryableStoreTypes.keyValueStore()
                        )
                );

        Customer customer = store.get(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public List<Customer> getCustomers() {

        ReadOnlyKeyValueStore<String, Customer> store =
                kafkaStreams.store(
                        StoreQueryParameters.fromNameAndType(
                                customerTableTopicName,
                                QueryableStoreTypes.keyValueStore()
                        )
                );

        List<Customer> customers = new ArrayList<>();

        try (KeyValueIterator<String, Customer> iterator =
                     store.all()) {

            while (iterator.hasNext()) {
                KeyValue<String, Customer> entry = iterator.next();
                customers.add(entry.value);
            }
        }

        return customers;
    }
}
