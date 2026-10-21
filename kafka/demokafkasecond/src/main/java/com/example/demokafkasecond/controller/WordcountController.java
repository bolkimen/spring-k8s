package com.example.demokafkasecond.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wordcount")
public class WordcountController extends BaseKafkaStreamsController {
    @Value(value = "${spring.kafka.wordcount-table-topic-name}")
    private String wordcountTableTopicName;

    @GetMapping
    public Map<String, Long> getWordCounts() {
        KafkaStreams kafkaStreams = getKafkaStreams();

        ReadOnlyKeyValueStore<String, Long> store =
                kafkaStreams.store(
                        StoreQueryParameters.fromNameAndType(
                                wordcountTableTopicName,
                                QueryableStoreTypes.keyValueStore()
                        )
                );

        Map<String, Long> wordCounts = new HashMap<>();

        try (KeyValueIterator<String, Long> iterator =
                     store.all()) {

            while (iterator.hasNext()) {
                KeyValue<String, Long> entry = iterator.next();
                wordCounts.put(entry.key, entry.value);
            }
        }

        return wordCounts;
    }
}
