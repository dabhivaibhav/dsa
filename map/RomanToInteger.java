package map;

import java.util.HashMap;
import java.util.Scanner;

public class RomanToInteger {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Symbol" + "\t" + "Value");
        System.out.println("I" + "\t" + "1");
        System.out.println("V" + "\t" + "5");
        System.out.println("X" + "\t" + "10");
        System.out.println("L" + "\t" + "50");
        System.out.println("C" + "\t" + "100");
        System.out.println("D" + "\t" + "500");
        System.out.println("M" + "\t" + "1000");

        System.out.print("Enter the roman number using the symbols (I, V, X, L, C, D and M): ");
        String str = sc.nextLine();
        romanToInteger(str);
    }

    private static void romanToInteger(String str) {

        int sum = 0;

        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);


        for (int i = 0; i < str.length(); i++) {
            if (i + 1 < str.length()) {
                if (map.get(str.charAt(i)) < map.get(str.charAt(i + 1))) {
                    sum -= map.get(str.charAt(i));
                } else {
                    sum += map.get(str.charAt(i));
                }
            } else {
                sum += map.get(str.charAt(i));
            }

        }
    }

}
