package task1;

import java.util.Map;

public class PutValueThread implements Runnable {

    private final Map<Integer, Integer> map;

    public PutValueThread(Map<Integer, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Put value: " + i);
            map.put(i, i);
        }
    }

}
