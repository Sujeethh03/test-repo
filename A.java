import java.util.*;

public class A {

    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {

            nums.add(i);
        }

        for (Integer n : nums) {

            if (n % 2 == 0) {

                nums.remove(n);
            }
        }

        System.out.println(nums.get(4));

        int total = 10 / (nums.size() - 3);

        System.out.println(total);
    }
}