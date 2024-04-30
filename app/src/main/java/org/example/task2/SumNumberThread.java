package org.example.task2;

import java.util.List;

public class SumNumberThread implements Runnable {

    private final List<Double> numbers;

    public SumNumberThread(List<Double> numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        do {
            synchronized (numbers) {
                double sum = 0;
                for (Double number : numbers) {
                    sum += number;
                }
                System.out.println("Sum: " + sum);
            }
        }while (numbers.size()<1000);
    }

}
