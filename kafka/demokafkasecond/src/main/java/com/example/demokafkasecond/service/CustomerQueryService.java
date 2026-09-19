package com.example.demokafkasecond.service;

import com.example.demokafkasecond.model.Customer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryService {
    @Value(value = "${spring.kafka.customers-table-topic-name}")
    private String customerTableTopicName;

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    public CustomerQueryService(
            StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    public Customer getCustomer(String id) {
        return getFromLocalStore(id);
    }

    private Customer getFromLocalStore(String id) {
        KafkaStreams kafkaStreams =
                streamsBuilderFactoryBean.getKafkaStreams();

        if (kafkaStreams == null) {
            throw new IllegalStateException(
                    "Kafka Streams has not started yet"
            );
        }
        ReadOnlyKeyValueStore<String, Customer> store =
                kafkaStreams.store(
                        StoreQueryParameters.fromNameAndType(
                                customerTableTopicName,
                                QueryableStoreTypes.keyValueStore()
                        )
                );

        return store.get(id);
    }

}
