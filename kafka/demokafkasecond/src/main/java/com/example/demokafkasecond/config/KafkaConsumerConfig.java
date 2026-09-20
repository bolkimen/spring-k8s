package com.example.demokafkasecond.config;

import com.example.demokafkasecond.model.Customer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.streams.application-id}")
    private String kafkaApplicationId;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value(value = "${spring.kafka.customers-topic-name}")
    private String customerTopicName;

    @Value(value = "${spring.kafka.customers-table-topic-name}")
    private String customerTableTopicName;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaApplicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer configurer() {
        return fb -> fb.setStateListener((newState, oldState) -> {
            System.out.println("State transition from " + oldState + " to " + newState);
        });
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder.stream("streamingTopic21");
        stream.peek((i, s) -> System.out.println("⚠️ kStream before for " + i + " with value " + s))
                .mapValues((ValueMapper<String, String>) String::toUpperCase)
                /*.groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMillis(1_000)))
                .reduce((String value1, String value2) -> value1 + value2,
                        Named.as("windowStore"))
                .toStream()
                .map((windowedId, value) -> new KeyValue<>(windowedId.key(), value))
                .filter((i, s) -> s.length() > 40)*/
                .peek((i, s) -> System.out.println("⚠️ kStream after for " + i + " with value " + s))
                .to("streamingTopic22");

        stream.print(Printed.<String, String>toSysOut().withLabel("orderValidate"));

        //stream.print(Printed.toSysOut());

        return stream;
    }

    @Bean
    public ConsumerFactory<String, String> consumerStrFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // or factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        //props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 7000);
        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // auto.offset.reset determines consumer behavior when it begins reading a partition without a committed offset or an invalid committed offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // earliest, latest, none
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        return props;
    }

    /*@Bean
    public KStream<String, String> resultKStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder.stream("streamingTopic22");
        //stream.peek((i, s) -> System.out.println("⚠️ resultKStream for {}" + i + " with value " + s));
        stream.print(Printed.<String, String>toSysOut().withLabel("orderValidate22"));
        return stream;
    }*/

    @Bean
    public KTable<String, Customer> customerTable(
            StreamsBuilder builder) {

        JacksonJsonSerde<Customer> customerSerde =
                new JacksonJsonSerde<>(Customer.class);

        return builder.table(
                customerTopicName,
                Materialized.<String, Customer, KeyValueStore<Bytes, byte[]>>
                                as(customerTableTopicName)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(customerSerde)
        );
    }
}
