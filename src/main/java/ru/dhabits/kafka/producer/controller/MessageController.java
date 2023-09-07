package ru.dhabits.kafka.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dhabits.kafka.producer.model.Message;
import ru.dhabits.kafka.producer.service.MessageSender;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;


@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageSender messageSender;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Message message) {
        if (messageSender.send(message)) {
            return status(OK)
                    .body("Ok");
        } else {
            return status(INTERNAL_SERVER_ERROR)
                    .body("kafka isn't available");
        }
    }
}
