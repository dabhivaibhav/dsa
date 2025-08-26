package array;

/*
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
Note that you must do this in-place without making a copy of the array.
Constraints:
            1 <= nums.length <= 10^5

Bruteforce approach: here I am using base cases check if the array is null or empty or has only one element. I am returning
with a message. After that I have taken a pointer "j" which is pointing to the next non-zero element.
Then I am iterating the array and whenever I find a non-zero element I am placing it at the position of "j" and incrementing "j".
After that I am filling the remaining positions with zeros. To fill that I will run a while loop until "j" is less than the length of the array.
and the while loop will start from the last increment of j from the for loop.
Time complexity: O(n), precise time complexity = O(n + k) where k is the number of non-zero elements.
Space complexity: O(1)
 */
public class MoveZerosToEnd {

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        bruteForceApproach(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }

    private static void bruteForceApproach(int[] nums) {

        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, operation has no effect.");
            return;
        }

        int n = nums.length;
        int j = 0; // Pointer for the position of the next non-zero element

        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                j++;
            }
        }

        // Fill the remaining positions with zeros
        while (j < n) {
            nums[j] = 0;
            j++;
        }
    }
}
