package array;

/*
Given an array, and an element num the task is to find if num is present in the given array or not. If present print the
index of the element or print -1.
 */
public class FindNumInArr {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int num = 10;
        System.out.println(findNumInArr(arr, num));
        System.out.println(findNumInArr(arr, 2));
        System.out.println(findNumInArr(arr, 1));

    }

    private static String findNumInArr(int[] arr, int num) {
        if (arr == null || arr.length == 0) return "Array is empty or null";

        if (arr.length == 1) {
            if (arr[0] == num) return "Element " + num + " found at index 0";
            else return "-1";
        }

        for (int index = 0; index < arr.length; index++) {
            if (arr[index] == num) {
                return "Element " + num + " is found at index " + index;
            }

        }
        return "-1";
    }
}
