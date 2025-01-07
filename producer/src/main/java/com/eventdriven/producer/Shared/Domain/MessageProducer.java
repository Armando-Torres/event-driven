package com.eventdriven.producer.shared.domain;

public interface MessageProducer {
    public void sendMessage(String topic, String key, String message);
}
