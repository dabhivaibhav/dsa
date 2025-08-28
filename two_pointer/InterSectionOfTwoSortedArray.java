package two_pointer;

import java.util.ArrayList;
import java.util.Arrays;

/*
You are given two sorted arrays nums1 and nums2 of sizes n and m respectively. Your task is to find the intersection of
these two arrays, which includes all the elements that are present in both arrays. The intersection should be returned
as a new array, and each element in the intersection should appear as many times as it shows in both arrays. The result
array should also be sorted in ascending order.

Approach: To solve this problem, we can use two pointers. One pointer "i" is used to traverse the first array and the
other pointer "j" is used to traverse the second array. Whenever the values at the two pointers are equal, we add the
value at the current index of both arrays to the result array. If the value at the pointer "i" is less than the value at
the pointer "j", we move the pointer "i" forward. If the value at the pointer "i" is greater than the value at the pointer
"j", we move the pointer "j" forward. If the value at the pointer "i" is equal to the value at the pointer "j", we move
both pointers forward.

 */
public class InterSectionOfTwoSortedArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 3, 3, 4, 5, 6};
        int[] nums2 = {2, 3, 3, 5, 6, 7};

        System.out.println(Arrays.toString(intersectionArray(nums1, nums2)));
    }

    private static int[] intersectionArray(int[] nums1, int[] nums2) {

        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }
        int i = 0;
        int j = 0;
        ArrayList<Integer> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                list.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else { // nums1[i] > nums2[j]
                j++;
            }
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
