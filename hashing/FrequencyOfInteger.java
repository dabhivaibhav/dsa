package hashing;


import java.util.HashMap;
import java.util.Scanner;

/*
You are given an array of integers. You need to find the frequency of each integer in the array.
You can use a HashMap to store the frequency of each integer as you iterate through the array
and then print the frequencies.
Constraints: 1 <= nums.length <= 10^5, -10^9 <= nums[i] <= 10^9


Approach: Here I've used a HashMap to store the frequency of each integer in the array.
1. Created a HashMap to store the frequency of each integer.
2. Iterate through the array and for each integer, update its frequency in the HashMap. Here to store the occurrence of
each integer, I've used the `put` method of the HashMap, and if the integer is not already present,
used `getOrDefault` to initialize its frequency to 0.
3. After iterating through the array, printing the frequency of each integer.
Time Complexity: O(n), where n is the number of elements in the array.
Space Complexity: O(n), where n is the number of unique integers in the array.

 */
public class FrequencyOfInteger {

    private static void findFrequency(int[] nums) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        System.out.println("Frequency of each integer in the array:");
        for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("Integer: " + entry.getKey() + ", Frequency: " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        System.out.print("Enter the size of the array:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        findFrequency(nums);
    }
}
