package recursion;


import java.util.Scanner;

/*
You are given a number n. You need to find the nth Fibonacci number using recursion.

                      fibonacci(4)
                     /            \
            fibonacci(3)         fibonacci(2)
           /         \            /         \
   fibonacci(2)   fibonacci(1) fibonacci(1) fibonacci(0)
    /      \
fibonacci(1) fibonacci(0)


When `fibonacci(4)` is called, it cannot return a value immediately, so it pauses and pushes itself onto the call stack
while it works on `fibonacci(3)`, which in turn pauses and calls `fibonacci(2)`; `fibonacci(2)` then calls `fibonacci(1)`
and `fibonacci(0)`, both of which hit the base case and return instantly. Once `fibonacci(2)` has both results, it
returns their sum to `fibonacci(3)`, which then calls `fibonacci(1)` for its other half. After `fibonacci(3)` gets both
values, it returns to `fibonacci(4)`, which then calls `fibonacci(2)` again, repeating the process. Each active function
call lives on the stack until its work is complete, and results flow back upward, unwinding the stack layer by layer
until the original call receives its final answer.
 */
public class Fibonacci {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        int result = fibonacci(n);
        System.out.println("The " + n + "th Fibonacci number is: " + result);
    }

    private static int fibonacci(int n) {

        if (n <= 1) {
            return n; // Base case: return n if it is 0 or 1
        }
        int last = fibonacci(n - 1); // Recursive call for n-1
        int secondLast = fibonacci(n - 2); // Recursive call for n-2
        // Return the sum of the last two Fibonacci numbers
        return last + secondLast;

    }

}
