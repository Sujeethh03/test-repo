
import java.io.*;

public class A {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("data.txt");

        
        int data = fis.read();
        System.out.println(data);
    }
}