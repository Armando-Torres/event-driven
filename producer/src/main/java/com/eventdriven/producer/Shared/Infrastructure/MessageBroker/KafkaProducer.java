package com.eventdriven.producer.Shared.Infrastructure.MessageBroker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.eventdriven.producer.Shared.Domain.MessageProducer;

@Service
public class KafkaProducer implements MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String message) {
        this.kafkaTemplate.send(topic, key, message);
        System.out.println("Message sent to Kafka topic " + topic + " with " + key + ": " + message);
    }
}
