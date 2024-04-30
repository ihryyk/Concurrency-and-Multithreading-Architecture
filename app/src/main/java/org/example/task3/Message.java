package org.example.task3;

public class Message {

    private final String topic;
    private final String payload;

    Message(String topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }

}
