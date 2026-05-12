// Severity 11 — Insecure Deserialization (Critical Security Vulnerability)
import java.io.*;

class User implements Serializable {
    String name;
}

public class B {

    public static void main(String[] args) throws Exception {

        FileInputStream file = new FileInputStream("object.ser");
        ObjectInputStream in = new ObjectInputStream(file);

        // Dangerous: untrusted deserialization
        User user = (User) in.readObject();

        System.out.println(user.name);

        in.close();
        file.close();
    }
}