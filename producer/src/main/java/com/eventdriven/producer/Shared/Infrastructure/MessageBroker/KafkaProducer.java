package com.eventdriven.producer.shared.infrastructure.messagebroker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eventdriven.producer.shared.domain.MessageProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class KafkaProducer implements MessageProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String message) {
        this.kafkaTemplate.send(topic, key, message);
        System.out.println("Message sent to Kafka topic " + topic + " with " + key + ": " + message);
    }
}
