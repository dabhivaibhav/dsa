package array;

/*
Given a binary array nums, return the maximum number of consecutive 1s in the array.A binary array is an array that
contains only 0s and 1s.
Constraints:
            1 <= nums.length <= 105
            nums[i] is either 0 or 1.
 */
public class MaximumConsecutiveones {

    public static void main(String[] args) {
        int[] nums = {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1};

        getMaximumConsecutiveOnes(nums);
    }


    /**
     *
     * i used two variables: 'sum' to count current sequence of ones, and 'max' to store the longest sequence.
     * When we find 1, we increment the sum. When we find 0, we update max if current sum is greater, and reset sum to 0.
     * At the end, we check one more time if the final sum is greater than max
     * Time Complexity: O(n) - where n is the length of input array
     * Space Complexity: O(1) - we only use two variables regardless of input size.
     * @param nums binary array containing only 0s and 1s
     */
    private static void getMaximumConsecutiveOnes(int[] nums) {
        int sum = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                sum++;
                max = Math.max(max, sum);
            } else {
                sum = 0;
            }
        }
        System.out.println("The maximum consecutive ones are: " + max);
    }
}
