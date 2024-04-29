package task3;

public class Consumer implements Runnable {

    private final String topic;
    private final MessageBus bus;

    Consumer(MessageBus bus, String topic) {
        this.bus = bus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = bus.take(topic);
                System.out.printf("Consumed message on topic %s: %s%n", message.getTopic(), message.getPayload());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
