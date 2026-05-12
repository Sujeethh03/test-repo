// Severity 9 — Race Condition / Concurrency Bug
class Counter {
    int count = 0;

    public void increment() {
        count++; // Not thread-safe
    }
}

public class A {
    public static void main(String[] args) throws Exception {

        Counter c = new Counter();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) {
                c.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) {
                c.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(c.count); // Expected 2000, may be less
    }
}