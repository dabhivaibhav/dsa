package hashing;


import java.util.Scanner;

/*
You are given an array of integers. You need to find the highest occurrence of any integer in the array. If there are
multiple elements that appear a maximum number of times, find the smallest of them.
You can use a HashMap to store the frequency of each integer as you iterate through the array.
Constraints: 1 <= n <= 10^5
             1 <= nums[i] <= 10^4

Approach: here I have used the hashin algorithm and instead of map I have used array as in the constraint the maximum
number would go up to 10^5 which is lower than array's capacity. To store the frequency of each integer, I have
used an array of size 10001 (to accommodate numbers from 0 to 10000). As we iterate through the input array, we increment
the count for each number in the frequency array and simultaneously check if the current number has a higher frequency
than the previously recorded maximum. If it does, we update the maximum frequency and the corresponding number. If the
frequency is the same as the maximum frequency, we check if the current number is smaller than the previously  recorded
number and update it if necessary.

Time Complexity: O(n)
Space Complexity: O(n)

 */
public class HighestOccurence {
    public static void main(String args[]) {

        System.out.print("Enter the size of the array: ");

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];

        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int highestOccurrence = nums[0];
        int maxFrequency = 1;

        int[] frequency = new int[10001]; // Since nums[i] <= 10^4, we can use an array of size 10001
        for (int num : nums) {
            frequency[num]++;
            if (frequency[num] > maxFrequency || (frequency[num] == maxFrequency && num < highestOccurrence)) {
                maxFrequency = frequency[num];
                highestOccurrence = num;
            }
        }
        System.out.println("The highest occurrence of " + highestOccurrence + "'s frequency is: " + maxFrequency);
    }
}
