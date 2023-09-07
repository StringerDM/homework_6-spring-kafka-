package ru.dhabits.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static ru.dhabits.kafka.config.KafkaProducerConfig.TOPIC_NAME;
import static ru.dhabits.kafka.config.KafkaProducerConfig.TOPIC_NAME_WITHOUT;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = {TOPIC_NAME, TOPIC_NAME_WITHOUT})
    public void messageTopic(@Payload String message,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_KEY) String key,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                             @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("Receive from Topic {} by key {} with offset {} from partition {} : {}", topic, key, offset, partition, message);
    }
}
