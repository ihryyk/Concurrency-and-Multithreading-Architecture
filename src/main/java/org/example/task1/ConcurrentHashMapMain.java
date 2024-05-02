package org.example.task1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapMain {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Thread putThread = new Thread(new PutValueThread(map));
        Thread sumThread = new Thread(new SumValuesThread(map));
        putThread.start();
        sumThread.start();
    }

}
