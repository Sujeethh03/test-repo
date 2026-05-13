import java.util.*;
import java.util.concurrent.*;

public class A {

    private static final Map<String, Integer> scores =
            new ConcurrentHashMap<>();

    public static void main(String[] args)
            throws Exception {

        ExecutorService executor =
                Executors.newCachedThreadPool();

        for (int i = 0; i < 500; i++) {

            int id = i;

            executor.submit(() -> {

                String key = "user-" + (id % 10);

                Integer value = scores.get(key);

                if (value == null) {

                    value = 0;
                }

                Thread.sleep(1);

                scores.put(key, value + 1);

                if (scores.get(key) > 20) {

                    scores.remove(key);
                }
            });
        }

        executor.shutdown();

        System.out.println(scores);
    }
}