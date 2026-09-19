package com.example.demokafkasecond.service;

import com.example.demokafkasecond.model.Customer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.streams.KafkaStreamsInteractiveQueryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerQueryService {
    @Value(value = "${spring.kafka.customers-table-topic-name}")
    private String customerTableTopicName;

    private final KafkaStreamsInteractiveQueryService queryService;
    private final RestClient restClient;

    public CustomerQueryService(
            KafkaStreamsInteractiveQueryService queryService,
            RestClient.Builder restClientBuilder) {

        this.queryService = queryService;
        this.restClient = restClientBuilder.build();
    }

    public Customer getLocalCustomer(String id) {

        HostInfo currentHost =
                queryService.getCurrentKafkaStreamsApplicationHostInfo();

        HostInfo hostInfo =
                queryService.getKafkaStreamsApplicationHostInfo(
                        customerTableTopicName,
                        id,
                        new StringSerializer()
                );

        ReadOnlyKeyValueStore<String, Customer> store =
                queryService.retrieveQueryableStore(
                        customerTableTopicName,
                        QueryableStoreTypes.keyValueStore()
                );

        return store.get(id);
    }

    public Customer getCustomer(String id) {

        HostInfo host = queryService
                .getKafkaStreamsApplicationHostInfo(
                        customerTableTopicName,
                        id,
                        new StringSerializer()
                );

        HostInfo currentHost =
                queryService.getCurrentKafkaStreamsApplicationHostInfo();

        if (currentHost.equals(host)) {
            return getFromLocalStore(id);
        }

        return getFromRemoteInstance(host, id);
    }

    private Customer getFromLocalStore(String id) {

        ReadOnlyKeyValueStore<String, Customer> store =
                queryService.retrieveQueryableStore(
                        customerTableTopicName,
                        QueryableStoreTypes.keyValueStore()
                );

        return store.get(id);
    }

    private Customer getFromRemoteInstance(
            HostInfo host,
            String id) {

        String url = "http://" +
                host.host() +
                ":" +
                host.port() +
                "/internal/customers/" +
                id;

        return restClient
                .get()
                .uri(url)
                .retrieve()
                .body(Customer.class);
    }
}
