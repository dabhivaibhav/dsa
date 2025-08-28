package hashing;

import java.util.HashMap;
import java.util.Map;

/*
You are given an array of integers. You need to find the element that appears only once in the array.
 */
public class FindElementAppearOnce {

    public static void main(String[] args) {

        int[] arr = {1, 1, 2, 9, 3, 3, 3, 3, 2, 4, 4,};
        int n = arr.length;
        System.out.println("Element that appears only once is: " + (findElementAppearOnce(arr)));
        System.out.println("Better approach: " + betterApproach(arr));
        System.out.println("Optimal approach: " + optimalApproach(arr));
    }

    /*
     * This method uses a hashing/counting approach to find the element that appears exactly once in the array.
     * The logic works as follows:
     * 1. Create a count array of size n+1 to store frequency of each element
     * 2. Iterate through input array and increment count for each element
     * 3. Scan through count array to find element with frequency=1
     * This approach can be used for similar problems where we need to find elements with specific frequency counts.
     * Time Complexity: O(n) for two separate array iterations
     * Space Complexity: O(n) for the count array
     */
    private static int findElementAppearOnce(int[] arr) {

        int[] count = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] == 1) return i;
        }
        return -1;
    }


    /*
    Approach: Here I've used a HashMap to store the frequency of each integer in the array.
    1. Created a HashMap to store the frequency of each integer.
    2. Iterate through the array and for each integer, update its frequency in the HashMap using getOrDefault.
    3. Iterate through the HashMap entries to find the element that appears exactly once.
    Time Complexity: O(n), where n is the number of elements in the array.
    Space Complexity: O(n), where n is the number of unique integers in the array.
     */
    private static long betterApproach(int[] arr) {

        HashMap<Long, Integer> map = new HashMap<>();

        for (int num : arr) {
            map.put((long) num, map.getOrDefault((long) num, 0) + 1);
        }

        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1)
                return entry.getKey();
        }
        return -1;
    }


    /*
     * This method uses XOR operations to find the element that appears exactly once.
     * The logic works as follows:
     * 1. XOR of a number with itself is 0
     * 2. XOR of a number with 0 is the number itself
     * 3. XOR all numbers - pairs cancel out, leaving single number
     * This approach is optimal as it:
     * - Requires only one pass through the array
     * - Uses no extra space
     * - Works with any number of duplicates (as long as others appear even times)
     * Time Complexity: O(n) for single array iteration
     * Space Complexity: O(1) as only one variable is used
     */
    private static int optimalApproach(int[] arr) {
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }
        return xor;
    }
}
