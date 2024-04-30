package org.example.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingObjectPool {

    private final Queue<Object> pool;
    private static final int DEFAULT_SIZE = 10;

    private final Lock lock = new ReentrantLock();
    private final Condition poolNotEmpty = lock.newCondition();
    private final Condition poolNotFull = lock.newCondition();

    public BlockingObjectPool(int size) {
        this.pool = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            this.pool.offer(new Object());
        }
    }

    public Object get() throws InterruptedException {
        lock.lock();
        try {
            while (this.pool.isEmpty()) {
                poolNotEmpty.await();
            }
            Object object = this.pool.poll();
            poolNotFull.signalAll();
            return object;
        } finally {
            lock.unlock();
        }
    }

    public void take(Object object) throws InterruptedException {
        lock.lock();
        try {
            while (pool.size() >= DEFAULT_SIZE) {
                poolNotFull.await();
            }
            pool.offer(object);
            poolNotEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
}