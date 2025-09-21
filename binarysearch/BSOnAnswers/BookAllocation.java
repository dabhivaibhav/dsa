package binarysearch.BSOnAnswers;

/*
Book Allocation Problem
Given an array nums of n integers, where nums[i] represents the number of pages in the i-th book, and an integer m
representing the number of students, allocate all the books to the students so that each student gets at least one book,
each book is allocated to only one student, and the allocation is contiguous.

Allocate the books to m students in such a way that the maximum number of pages assigned to a student is minimized. If
the allocation of books is not possible, return -1.

Examples:
Input: nums = [12, 34, 67, 90], m=2
Output: 113
Explanation: The allocation of books will be 12, 34, 67 | 90. One student will get the first 3 books and the other will
get the last one.

Input: nums = [25, 46, 28, 49, 24], m=4
Output: 71
Explanation: The allocation of books will be 25, 46 | 28 | 49 | 24.

Constraints:
          1 <= n, m <= 10^4
          1 <= nums[i] <= 10^5
 */
public class BookAllocation {

    public static void main(String[] args) {
        int[] nums = {12, 34, 67, 90};
        int m = 2;
        int[] nums1 = {25, 46, 28, 49, 24};
        int m1 = 4;

        System.out.println(bookAllocationOptimal(nums1, m1));
        System.out.println(bookAllocationOptimal(nums, m));
    }


    /**
     * bookAllocationOptimal
     * <p>
     * What it does:
     * Finds the minimum possible value of the maximum pages assigned to any student, given that
     * each student must receive a contiguous block of books and every book must be assigned.
     * Returns -1 if allocation is impossible (e.g., m > number of books).
     * <p>
     * Core idea (binary search on the answer):
     * - Suppose we “guess” a capacity C = maximum pages a single student is allowed to carry.
     * - Using a greedy check, we see how many students are needed if no one exceeds C.
     * If we can do it with ≤ m students, C is feasible; otherwise C is too small.
     * - Because feasibility is monotonic in C (bigger C never hurts), we can binary search on C.
     * <p>
     * Choosing the search range (low and high):
     * - Natural lower bound: max(nums) — someone must take the largest single book.
     * - Natural upper bound: sum(nums) — one student takes all books.
     * - This implementation initializes low as the minimum book size and high as the sum of all pages.
     * Intuition: any C smaller than the largest book will be rejected by the feasibility check,
     * so the search will move low upward until it passes max(nums). Using min(nums) still converges,
     * but starting at max(nums) is the tight, standard bound.
     * <p>
     * Walkthrough example:
     * nums = [12, 34, 67, 90], m = 2
     * - max(nums) = 90, sum(nums) = 203
     * - Try C = 146: Greedy packing yields ≤ 2 students → feasible → try smaller C.
     * - Try C = 113: Greedy packing gives [12,34,67] and [90] → 2 students → feasible → try smaller.
     * - Try C = 100: Greedy packing needs 3 students → not feasible → increase C.
     * - The smallest feasible C is 113, which is the answer.
     * <p>
     * Why the loop updates look like this:
     * - If isPossible(C) == true, we record ans = C and shrink the right boundary (high = C - 1)
     * to hunt for a smaller feasible maximum.
     * - If isPossible(C) == false, we raise the left boundary (low = C + 1) to allow more capacity.
     * - When the loop finishes, ans is the minimum feasible maximum pages.
     * <p>
     * Edge handling:
     * - If nums is null/empty → return -1 (no allocation).
     * - If m > nums.length → return -1 (can’t give each student at least one book).
     * <p>
     * Time complexity:
     * - Let n = nums.length, S = sum(nums).
     * - Each feasibility check is O(n). Binary search ranges over ~log(S).
     * - Total ≈ O(n log S).
     * <p>
     * Space complexity:
     * - O(1) extra; no auxiliary structures.
     */
    private static int bookAllocationOptimal(int[] nums1, int m1) {
        int low = Integer.MAX_VALUE;
        int high = 0;
        int ans = -1;

        if (nums1.length == 0 || nums1 == null) return -1;
        if (m1 > nums1.length) return -1;
        for (int num : nums1) {
            low = Math.min(low, num);
            high += num;
        }
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isPossible(nums1, m1, mid)) {
                ans = mid;
                high = mid - 1;

            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    /**
     * isPossible
     * <p>
     * What it does:
     * Greedy feasibility check for a given capacity mid (C):
     * Returns true if we can split the array into ≤ m contiguous segments such that
     * the sum of each segment is ≤ mid; otherwise false.
     * <p>
     * Greedy logic:
     * - Start with the first student and a running sum = 0.
     * - For each book pages:
     * sum += pages
     * If sum exceeds mid, we “cut” here:
     * increment the student count and start a new segment with sum = pages.
     * - If at any time the number of students exceeds m, mid is too small → return false.
     * - If we finish scanning with students ≤ m, mid is feasible → return true.
     * <p>
     * Why this is correct:
     * - For a fixed capacity C, packing left-to-right and cutting only when we must exceed C
     * uses the fewest possible students for that C. If even this greedy packing needs > m students,
     * no other contiguous partition can do it with ≤ m.
     * - This monotonic predicate (feasible for large C; infeasible for small C) enables binary search.
     * <p>
     * Example with mid = 113 on nums = [12, 34, 67, 90], m = 2:
     * - sum=0
     * add 12 → 12
     * add 34 → 46
     * add 67 → 113 (exactly C)
     * next 90 would exceed → cut → students=2, sum=90
     * - Finished with students=2 ≤ m → feasible.
     * <p>
     * Time complexity:
     * - O(n): single pass over the books.
     * <p>
     * Space complexity:
     * - O(1).
     */
    private static boolean isPossible(int[] nums1, int m1, int mid) {
        int students = 1;
        int sum = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum += nums1[i];
            if (sum > mid) {
                students++;
                sum = nums1[i];
            }
            if (students > m1) {
                return false;
            }
        }
        return true;
    }


}
