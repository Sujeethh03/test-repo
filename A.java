import java.io.*;

public class A {

    public static void main(String[] args) throws Exception {

        String userInput = args[0];

        Process process =
                Runtime.getRuntime().exec(userInput);
        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );

        String line;

        while ((line = br.readLine()) != null) {

            System.out.println(line);
        }
        System.out.println(process.exitValue());
    }
}