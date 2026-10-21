package com.example.demokafkasecond.service;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordCountProcessor {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Value(value = "${spring.kafka.wordcount-input-topic}")
    private String wordcountInputTopic;

    @Value(value = "${spring.kafka.wordcount-output-topic}")
    private String wordcountOutputTopic;

    @Value(value = "${spring.kafka.wordcount-table-topic-name}")
    private String wordcountTableTopicName;

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        /*KStream<String, String> messageStream = streamsBuilder
                .stream("input-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        KTable<String, Long> wordCounts = messageStream
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
                .count();

        wordCounts.toStream().to("output-topic");*/

        // 1. Read input topic as KStream
        KStream<String, String> textLines = streamsBuilder.stream(wordcountInputTopic);

// 2. Process, split, group, and count into a KTable
        KTable<String, Long> wordCounts = textLines
                .mapValues(value -> value.toLowerCase())
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .groupBy((key, word) -> word)
                .count(Materialized.as(wordcountTableTopicName));

// 3. Convert KTable back to KStream and write to output topic
        wordCounts.toStream()
                .to(wordcountOutputTopic, Produced.with(Serdes.String(), Serdes.Long()));
    }
}
