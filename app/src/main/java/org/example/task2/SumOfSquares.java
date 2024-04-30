package org.example.task2;

import java.util.List;

public class SumOfSquares implements Runnable {

    private final List<Double> numbers;

    public SumOfSquares(List<Double> numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        do {
            synchronized (numbers) {
                double sumOfSquares = 0;
                for (Double number : numbers) {
                    sumOfSquares += number * number;
                }
                System.out.println("Square root of sum of squares: " + Math.sqrt(sumOfSquares));
            }
        } while (numbers.size() < 1000);
    }

}
