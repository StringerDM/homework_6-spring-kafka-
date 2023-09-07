package ru.dhabits.kafka.producer.model;

import lombok.Data;

@Data
public class Message {

    private String messageText;
    private String topic;
    private String key;
}
