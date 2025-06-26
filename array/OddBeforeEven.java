package array;

import java.util.Arrays;

/**
 * Write a program that places odd before even in an array
 */
public class OddBeforeEven {

    public static void main(String[] args) {

        int[] arr = {1, 2, 4, 5, 7, 9, -2, 3};

        int[] copyArr = new int[arr.length];

        int j = 0;
        int k = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i] % 2 == 0) {
                copyArr[k] = arr[i];
                k--;
            } else {
                copyArr[j] = arr[i];
                j++;
            }
        }
        System.out.println(Arrays.toString(copyArr));
    }
}
