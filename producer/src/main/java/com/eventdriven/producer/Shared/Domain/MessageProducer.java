package com.eventdriven.producer.Shared.Domain;

public interface MessageProducer {
    public void sendMessage(String topic, String key, String message);
}
