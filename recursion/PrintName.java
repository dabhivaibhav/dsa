package recursion;


import java.util.Scanner;

/*
You are given an integer n. You need to print your name n times using recursion.
 */
public class PrintName {


    private static void printName(int i, int n) {
        if (i > n) return;
        System.out.println("Vaibhav");
        printName(i + 1, n);
    }

    public static void main(String[] args) {

        System.out.print("Enter a positive integer: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int i = 1;
        printName(i, n);
    }


}
