package task4;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingObjectPool {

    private final Queue<Object> pool;
    private static final int DEFAULT_SIZE = 10;

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.pool = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            this.pool.offer(new Object());
        }
    }

    public synchronized Object get() throws InterruptedException {
        while (this.pool.isEmpty()) {
            wait();
        }
        Object object = this.pool.poll();
        notifyAll();
        return object;
    }

    public synchronized void take(Object object) throws InterruptedException {
        while (pool.size() >= DEFAULT_SIZE) {
            wait();
        }
        pool.offer(object);
        notifyAll();
    }

}
