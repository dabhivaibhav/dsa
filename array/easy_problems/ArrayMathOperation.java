package array.easy_problems;

/**
 * Write a program that displays the sum,
 * product, and average of the elements
 * of an integer array
 */
public class ArrayMathOperation {

    public static int sum(int[] intArr) {
        int sum = 0;
        for (int i = 0; i < intArr.length; i++) {
            sum += intArr[i];
        }
        return sum;
    }

    public static void product(int[] intArr) {
        int product = 1;
        for (int i = 0; i < intArr.length; i++) {
            product *= intArr[i];
        }
        System.out.println("Product: "+product);
    }

    public static void average(int[] intArr) {
        int sum = sum(intArr);
        double average = (double) sum / intArr.length;
        System.out.println("Average: "+average);
    }

    public static void main(String[] args) {

        int[] intArr = {1, 2, 3, 4, 51};
        System.out.println("Sum: "+sum(intArr));
        average(intArr);
        product(intArr);

    }
}
