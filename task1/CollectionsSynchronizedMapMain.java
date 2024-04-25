package task1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsSynchronizedMapMain {

    public static void main(String[] args) {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        Thread putThread = new Thread(new PutValueThread(map));
        Thread sumThread = new Thread(new SumValuesThread(map));
        putThread.start();
        sumThread.start();
    }

}
