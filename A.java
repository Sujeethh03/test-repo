import java.io.*;
import java.util.*;

public class A {

    private static List<String> logs =
            new ArrayList<>();

    public static void main(String[] args)
            throws Exception {

        Scanner sc =
                new Scanner(new File("users.txt"));

        Map<String, Integer> users =
                new HashMap<>();

        while (sc.hasNextLine()) {

            String line = sc.nextLine();

            String[] parts = line.split(",");

            users.put(parts[0],
                    Integer.parseInt(parts[1]));
        }

        for (String name : users.keySet()) {

            if (users.get(name) < 18) {

                users.remove(name);
            }

            logs.add(name + ":" + users.get(name));
        }

        Collections.sort(logs);

        System.out.println(logs.get(10));

        int avg =
                100 / users.size();

        System.out.println(avg);

        sc.close();
    }
}