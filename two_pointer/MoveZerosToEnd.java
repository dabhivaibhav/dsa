package two_pointer;

/*
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
Note that you must do this in-place without making a copy of the array.
Constraints:
            1 <= nums.length <= 10^5

Here I have used two pointer approach. So first I get the base cases check if the array is null or empty or has only one element. I am returning
with a message. After that I have taken two pointers "i" and "j". "i" is used to traverse the array and "j" is pointing to the next non-zero element.
Then I am iterating the array and whenever I find a non-zero element I am swapping it with the element at the position of "j" and incrementing "j".
Time complexity: O(n)
Space complexity: O(1)
 */
public class MoveZerosToEnd {

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12, 0, 12};
        moveZeroToEnd(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }

    private static void moveZeroToEnd(int[] nums) {

        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, operation has no effect.");
            return;
        }
        int j = 0; // Pointer for the position of the next non-zero element
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // swap
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++;
            }
        }
    }
}
