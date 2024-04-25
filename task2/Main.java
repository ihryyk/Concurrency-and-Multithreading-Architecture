package task2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final List<Double> numbers = new ArrayList<>();
        Thread putThread = new Thread(new PutNamberThread(numbers));
        Thread sumThread = new Thread(new SumNumberThread(numbers));
        Thread sumSquaresThread = new Thread(new SumOfSquares(numbers));

        putThread.start();
        sumThread.start();
        sumSquaresThread.start();
    }

}
