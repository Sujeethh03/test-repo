import java.util.*;
import java.util.concurrent.*;

public class A {

    private static Map<Integer, String> cache =
            new HashMap<>();

    public static void main(String[] args) throws Exception {

        ExecutorService executor =
                Executors.newFixedThreadPool(4);

        List<Future<?>> futures =
                new ArrayList<>();

        for (int i = 0; i < 1000; i++) {

            final int id = i;

            futures.add(
                    executor.submit(() -> {

                        String value =
                                cache.get(id);

                        if (value == null) {

                            value =generateValue(id);

                            cache.put(id, value);
                        }

                        if (id % 50 == 0) {

                            cache.clear();
                        }

                        System.out.println(
                                cache.get(id).length()
                        );
                    })
            );
        }

        for (Future<?> future : futures) {

            future.get();
        }

        executor.shutdown();
    }

    private static String generateValue(int id)
            throws InterruptedException {

        Thread.sleep(1);

        return UUID.randomUUID().toString() + id;
    }
}