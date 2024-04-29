package task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageBus {

    private final Map<String, List<Message>> topicMap = new HashMap<>();
    private static final int CAPACITY = 10;

    public synchronized void put(Message message) throws InterruptedException {
        topicMap.putIfAbsent(message.getTopic(), new ArrayList<>());
        while (topicMap.get(message.getTopic()).size() >= CAPACITY) {
            wait();
        }
        topicMap.get(message.getTopic()).add(message);
        System.out.println("Published: " + message.getTopic() + "( " + message.getPayload() + " )");
        System.out.println("Topic " + message.getTopic() +" size: " + topicMap.get(message.getTopic()).size());
        notifyAll();
    }

    public synchronized Message take(String topic) throws InterruptedException {
        while (!this.topicMap.containsKey(topic) || this.topicMap.get(topic).isEmpty()) {
            System.out.println("Messages in topic " + topic + " not found");
            wait();
        }
        Message message = this.topicMap.get(topic).removeFirst();
        notifyAll();
        return message;
    }

}
