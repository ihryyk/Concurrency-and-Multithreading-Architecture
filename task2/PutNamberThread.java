package task2;

import java.util.List;
import java.util.Random;

public class PutNamberThread implements Runnable {

    private final List<Double> numbers;

    public PutNamberThread(List<Double> numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            synchronized (numbers) {
                double randomNumber = random.nextDouble();
                System.out.println("Put number: " + randomNumber);
                numbers.add(randomNumber);
            }
        }
    }

}
