package org.example.task3;

import java.util.Random;
import java.util.UUID;

public class Producer implements Runnable {

    private static final String[] TOPICS = {"Sport", "Weather", "Ukraine"};
    private final Random random = new Random();
    private final MessageBus bus;

    Producer(MessageBus bus) {
        this.bus = bus;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            String topic = TOPICS[random.nextInt(TOPICS.length)];
            Message message = new Message(topic, UUID.randomUUID().toString());
            try {
                bus.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
