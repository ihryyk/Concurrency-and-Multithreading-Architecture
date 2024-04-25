package task1;

import java.util.Map;

public class SumValuesThread implements Runnable {

    private final Map<Integer, Integer> map;

    public SumValuesThread(Map<Integer, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        do {
            int sum = 0;
            for (Integer value : map.values()) {
                sum += value;
            }
            System.out.println("The sum is: " + sum);
        } while (map.size() < 1000);

    }

}
