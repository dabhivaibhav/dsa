package two_pointer;


import java.util.*;

/*
Given two sorted arrays nums1 and nums2, return an array that contains the union of these two arrays. The elements in
the union must be in ascending order. The union of two arrays is an array where all values are distinct and are present
in either the first array, the second array, or both.
Constraints:
            1 <= nums1.length, nums2.length <= 1000
            -104 <= nums1[i] , nums2[i] <= 104
            Both nums1 and nums2 are sorted in non-decreasing order


BruteForce: To insert unique elements in the result array I am using a Treeset. as the given arrays are sorted. To insert
elements  I can simply iterate through the first array and add the elements to the Treeset. Then I iterate through the
second array and add the elements to the Treeset. Finally I iterate through the Treeset and add the elements to the
result array. so here the time complexity is O((n+k)logn)  where n + k is the total number of elements in both arrays
and "logn" is the time complexity to insert an element in the Treeset. and the space complexity is O(n+k)
Time Complexity: O(n + k (logn))
Space Complexity: O(n + k)

Optimal Approach: Here I am using two pointer approach. For that I have taken two pointers i and j. I am iterating
through both the array at the same time. so I will compare the elements at both pointers. If the element at
pointer i is less than the element at pointer j, then I will add the element at pointer i to the result array and i
ncrement the pointer i. If the element at pointer j is less than the element at pointer i, then I will add the element
at pointer j to the result array.But before that adding elements at pointer i and j I am checking if the element at
previous index of i-1 or j-1 is not equal. If it is equal then I will not add the element and keep moving the pointers.
so the time complexity of this approach is O(n+k) and the space complexity is O(n+k) where n + k is the total number of
elements in both arrays.
Time Complexity: O(n + k)
Space Complexity: O(n + k)
 */
public class UnionOfTwoSortedArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 1, 2, 3};
        int[] nums2 = {1, 3, 4, 5};
        int[] result = bruteForceApproach(nums1, nums2);
        System.out.print("Brute Force Approach: " + Arrays.toString(result));
        System.out.println("\nOptimal Approach: " + Arrays.toString(twoPointerApproach(nums1, nums2)));
    }

    private static int[] bruteForceApproach(int[] nums1, int[] nums2) {

        Set<Integer> set = new TreeSet<>();

        for (int num : nums1) {
            set.add(num);
        }
        for (int num : nums2) {
            set.add(num);
        }
        int[] result = new int[set.size()];
        int index = 0;


        for (int num : set) {
            result[index++] = num;
        }
        return result;
    }

    private static int[] twoPointerApproach(int[] nums1, int[] nums2) {

        ArrayList<Integer> list = new ArrayList<>();

        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                if (i == 0 || nums1[i - 1] != nums1[i]) {
                    list.add(nums1[i]);
                }
                i++;
            } else if (nums1[i] > nums2[j]) {
                if (j == 0 || nums2[j - 1] != nums2[j]) {
                    list.add(nums2[j]);
                }
                j++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }

        while (i < nums1.length) {
            if (i == 0 || nums1[i - 1] != nums1[i]) {
                list.add(nums1[i]);
            }
            i++;
        }

        while (j < nums2.length) {
            if (j == 0 || nums2[j - 1] != nums2[j]) {
                list.add(nums2[j]);
            }
            j++;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
