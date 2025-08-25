package two_pointer;

import java.util.Arrays;

/*
Given an integer array nums, rotate the array to the left by given K. Note: There is no need to return anything, just modify the given array.
Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4


Here I have used two pointer approach. So first I get the "K" and I took "K%n" because you have to perform the rotation by the modulo you are getting.
Then I reverse the array from k to n-1, then from 0 to k-1 and finally from 0 to n-1.
Time complexity: O(n)
Space complexity: O(1)

I have used another approach also in which I am rotating the array by one k times. but in that I have used 2 for loops.
In first loop I am iterating k times and in second loop I am shifting the elements to left by one.
Time complexity: O(n*k)
Space complexity: O(1)
 */
public class LeftRotateArrayByK {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int[] nums1 = {1, 2, 3, 4, 5};
        int k = 3; // Number of positions to rotate left
        System.out.println("Array before left rotation by " + k + ":");
        Arrays.toString(nums);
        System.out.println("Array after left rotation by " + k + ":");
        leftRotateByK(nums, k);
        System.out.println("\nAnother approach for left rotation by " + k + ":");
        anotherApproach(nums1, k);
    }

    private static void leftRotateByK(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform rotation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, rotation has no effect.");
            return;
        }

        int n = nums.length;
        k = k % n;

        reverseArr(nums, k, n - 1);
        reverseArr(nums, 0, k - 1);
        reverseArr(nums, 0, n - 1);

        System.out.println(Arrays.toString(nums));
    }

    private static void anotherApproach(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform rotation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, rotation has no effect.");
            return;
        }

        int n = nums.length;
        k = k % n;

        for(int i = 0; i < k; i++) {
            int firstElement = nums[0];
            for (int j = 1; j < n; j++) {
                nums[j - 1] = nums[j];
            }
            nums[n - 1] = firstElement;
        }

        System.out.println(Arrays.toString(nums));
    }
    private static void reverseArr(int[] nums, int low, int high) {

        while (low < high) {

            int temp = nums[low];
            nums[low] = nums[high];
            nums[high] = temp;
            low++;
            high--;
        }

    }
}
