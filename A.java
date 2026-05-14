import java.io.*;
import java.util.*;

public class A {

    private static Map<String, String> sessions =
            new HashMap<>();

    public static void main(String[] args)
            throws Exception {

        BufferedReader br =
                new BufferedReader(
                        new FileReader("sessions.txt")
                );

        String line;

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(":");

            sessions.put(parts[0], parts[1]);
        }

        List<String> activeUsers =
                new ArrayList<>(sessions.keySet());

        for (String user : activeUsers) {

            if (user.startsWith("admin")) {

                activeUsers.remove(user);
            }
        }

        Collections.sort(activeUsers);

        System.out.println(
                activeUsers.get(5).toUpperCase()
        );

        String token =
                sessions.get(activeUsers.get(0));

        if (token.length() > 10) {

            System.out.println(token.substring(0, 20));
        }

        int result =
                500 / activeUsers.size();

        System.out.println(result);

        br.close();
    }
}