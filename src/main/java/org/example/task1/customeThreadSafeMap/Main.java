package org.example.task1.customeThreadSafeMap;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        CustomThreadSafeMap<Integer, Integer> map = new CustomThreadSafeMap<>(new HashMap<>());

        Thread putThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Put value: " + i);
                map.put(i, i);
            }
        });

        Thread readThread = new Thread(() -> {
            do {
                int sum = 0;
                for (Integer value : map.values()) {
                    sum += value;
                }
                System.out.println("The sum is: " + sum);
            } while (map.size() < 1000);
        });

        putThread.start();
        readThread.start();
    }

}
