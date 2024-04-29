package task4;

public class Main {

    public static void main(String[] args) {
        final int poolSize = 5;
        final int totalTasks = 20;
        final BlockingObjectPool pool = new BlockingObjectPool(poolSize);
        Thread[] threads = new Thread[totalTasks];

        for (int i = 0; i < totalTasks; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Object object = pool.get();
                    System.out.println("Thread " + Thread.currentThread().getName() + " took an object from the pool.");
                    pool.take(object);
                    System.out.println(
                            "Thread " + Thread.currentThread().getName() + " returned an object to the pool.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread was interrupted", e);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < totalTasks; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }

        System.out.println("Done!");
    }

}
