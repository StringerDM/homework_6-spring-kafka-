package ru.dhabits.kafka.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.dhabits.kafka.producer.model.Message;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final KafkaTemplate<String, String> template;

    public boolean send(Message message) {
        CompletableFuture<SendResult<String, String>> future = template.send(message.getTopic(), message.getKey(), message.getMessageText());

        try {
            SendResult<String, String> result = future.get();
            log.info("Successful send to {} by key {} with offset {} to partition {}",
                    result.getProducerRecord().topic(), message.getKey(), result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());
            return true;
        } catch (ExecutionException | InterruptedException e) {
            log.error("Cannot send message to Kafka Topic {}", message.getTopic(), e);
        }
        return false;
    }
}
