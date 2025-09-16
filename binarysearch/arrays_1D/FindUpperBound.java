package binarysearch.arrays_1D;


/*
Upper Bound
Given a sorted array of nums and an integer x, write a program to find the upper bound of x.
The upper bound algorithm finds the first and smallest index in a sorted array where the value at that index is greater
than a given key i.e. x. If no such index is found, return the size of the array.

Examples:
Input : n= 4, nums = [1,2,2,3], x = 2
Output:3
Explanation: Index 3 is the smallest index such that arr[3] > x.

Input : n = 5, nums = [3,5,8,15,19], x = 9
Output: 3
Explanation: Index 3 is the smallest index such that arr[3] > x.

Constraints:
  1 <= nums.length <= 10^5
  -10^5 < nums[i], x < 10^5
  nums is sorted in ascending order.
 */
public class FindUpperBound {

    public static void main(String[] args) {

        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 4;
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 12;

        upperBoundBruteForce(nums, target);
        upperBoundBruteForce(nums1, target1);
        upperBoundOptimal(nums, target);
        upperBoundOptimal(nums1, target1);
    }

    public static void upperBoundBruteForce(int[] nums, int x) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] > x) {
                System.out.println("Upper bound of " + x + " is " + i);
                return;
            }
        }
        System.out.println("Upper bound of " + x + " is " + n);
    }

    public static void upperBoundOptimal(int[] nums, int x) {
        int low = 0;
        int high = nums.length - 1;
        int ans = nums.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] > x){
                ans = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        System.out.println("Upper bound of " + x + " is " + ans);
    }
}
