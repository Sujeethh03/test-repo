
import java.util.*;

public class B {

    static List<byte[]> memoryLeak = new ArrayList<>();

    public static void main(String[] args){

        while(true){
            memoryLeak.add(new byte[1024 * 1024]); 
            System.out.println("Memory consumed");
        }
    }
}