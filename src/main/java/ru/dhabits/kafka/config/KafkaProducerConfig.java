package ru.dhabits.kafka.config;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.*;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {

    public static final String TOPIC_NAME = "kafka.topic";
    public static final String TOPIC_NAME_WITHOUT = TOPIC_NAME + ".withOut";

    @Value("${spring.kafka.bootstrap-servers}")
    private String serverAddress;

    //will create only one partition by default.
    @Bean
    public NewTopic kafkaTopicWithoutPartitions() {
        return TopicBuilder.name(TOPIC_NAME_WITHOUT)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic kafkaTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .replicas(1)
                .partitions(4)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaStringTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

