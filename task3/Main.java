package task3;

public class Main {

    public static void main(String[] args) {
        MessageBus bus = new MessageBus();
        new Thread(new Producer(bus)).start();
        new Thread(new Producer(bus)).start();
        new Thread(new Consumer(bus, "Ukraine")).start();
        new Thread(new Consumer(bus, "Sport")).start();
        new Thread(new Consumer(bus, "Weather")).start();
    }
}
