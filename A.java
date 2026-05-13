import java.io.*;

public class A {

    public static void main(String[] args) throws Exception {

        File file = new File("data.txt");

        BufferedReader br =
                new BufferedReader(
                        new FileReader(file)
                );

        String line;

        while ((line = br.readLine()) != null) {

            if (line.contains(args[0])) {

                System.out.println(line.toLowerCase());
            }
        }

        br.close();
    }
}