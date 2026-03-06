package heap.medium_problem;

import java.util.*;

/*
Leetcode 347: Top K Frequent Elements

Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

Example 1:
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]

Example 2:
Input: nums = [1], k = 1
Output: [1]

Example 3:
Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
Output: [1,2]

Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            k is in the range [1, the number of unique elements in the array].
            It is guaranteed that the answer is unique.

Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class TopKFrequentElements {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(Arrays.toString(topKFrequentBruteForce(nums, k)));
        System.out.println(Arrays.toString(topKFrequentOptimal(nums, k)));
        nums = new int[]{1};
        k = 1;
        System.out.println(Arrays.toString(topKFrequentBruteForce(nums, k)));
        System.out.println(Arrays.toString(topKFrequentOptimal(nums, k)));
    }

    /**
     * topKFrequentBruteForce(int[] nums, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k most frequent elements
     * from the array nums.
     * <p>
     * The elements can be returned
     * in any order.
     * <p>
     * Core Idea:
     * <p>
     * Instead of sorting all elements
     * by their frequency (which would take
     * O(n log n)), we keep only the
     * top k frequent elements
     * using a Min Heap.
     * <p>
     * The heap always stores the
     * k most frequent numbers seen so far.
     * <p>
     * If a new element has a higher frequency
     * than the smallest element in the heap,
     * it replaces it.
     * <p>
     * Thought Process:
     * <p>
     * Step 1:
     * <p>
     * Count the frequency of every number
     * using a HashMap.
     * <p>
     * Example:
     * <p>
     * nums = [1,1,1,2,2,3]
     * <p>
     * Frequency Map:
     * <p>
     * 1 → 3
     * 2 → 2
     * 3 → 1
     * <p>
     * Step 2:
     * <p>
     * Use a Min Heap (PriorityQueue)
     * that stores numbers based on
     * their frequency.
     * <p>
     * The number with the smallest
     * frequency stays at the top.
     * <p>
     * Step 3:
     * <p>
     * Traverse all unique numbers
     * from the frequency map.
     * <p>
     * Insert each number into the heap.
     * <p>
     * If the heap size becomes
     * larger than k,
     * remove the smallest frequency element.
     * <p>
     * This ensures the heap always
     * keeps only the k most frequent numbers.
     * <p>
     * Step 4:
     * <p>
     * Extract the elements
     * from the heap and place them
     * into the result array.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Build a frequency map
     * using HashMap.
     * <p>
     * Step 2:
     * <p>
     * Create a Min Heap that compares
     * numbers based on their frequency:
     * <p>
     * map.get(a) - map.get(b)
     * <p>
     * Step 3:
     * <p>
     * Iterate through each unique number
     * in the map.
     * <p>
     * Add it to the heap.
     * <p>
     * If heap size exceeds k,
     * remove the smallest frequency element.
     * <p>
     * Step 4:
     * <p>
     * Extract elements from the heap
     * and store them in the result array.
     * <p>
     * Example 1:
     * <p>
     * nums = [1,1,1,2,2,3]
     * k = 2
     * <p>
     * Frequency Map:
     * <p>
     * 1 → 3
     * 2 → 2
     * 3 → 1
     * <p>
     * Heap keeps the two most frequent:
     * <p>
     * [1,2]
     * <p>
     * Output → [1,2]
     * <p>
     * Example 2:
     * <p>
     * nums = [1]
     * k = 1
     * <p>
     * Frequency Map:
     * <p>
     * 1 → 1
     * <p>
     * Heap → [1]
     * <p>
     * Output → [1]
     * <p>
     * Complexity:
     * <p>
     * Let:
     * <p>
     * n = number of elements
     * m = number of unique elements
     * <p>
     * Time Complexity:
     * <p>
     * O(n + m log k)
     * <p>
     * - O(n) to build the frequency map
     * - O(m log k) for heap operations
     * <p>
     * Space Complexity:
     * <p>
     * O(m + k)
     * <p>
     * - m for the frequency map
     * - k for the heap
     * <p>
     * Interview Takeaway:
     * <p>
     * When a problem asks for
     * "Top K" elements,
     * a Min Heap of size k
     * is usually the optimal strategy.
     * <p>
     * Instead of sorting everything,
     * we keep only the best k candidates,
     * which dramatically reduces
     * the total work.
     */
    private static int[] topKFrequentBruteForce(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));

        for (int num : map.keySet()) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        while (!minHeap.isEmpty()) {
            result[--k] = minHeap.poll();
        }
        return result;
    }


    /*
    THE "FREQUENCY BUCKET" PATTERN

    THE LIMITATION OF HEAPS:
    A Heap still has to 're-organize' itself every time you
    add an element (log K).

    THE POWER OF BUCKETS:
    If we know the 'Range' of our frequencies (0 to N),
    we can use the frequency AS THE INDEX.
    Accessing an index in an array is O(1).

    AHA! MOMENT:
    Instead of asking 'Which number is most frequent?',
    we create a list for every possible frequency count
    and just fill them up. Walking backward through the
    frequencies is like reading a Top-10 chart from #1 down.

    WHEN TO USE:
    Use Bucket Sort when the 'Values' you are sorting by
    (frequencies) are bounded by the size of the input.
    */


    /**
     * topKFrequentOptimal(int[] nums, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k most frequent elements
     * from the array nums.
     * <p>
     * Instead of using a heap, this method
     * uses the Bucket Sort idea to achieve
     * better performance.
     * <p>
     * Core Idea:
     * <p>
     * The frequency of any number in the array
     * cannot exceed nums.length.
     * <p>
     * That means the possible frequencies
     * range from:
     * <p>
     * 1 → nums.length
     * <p>
     * So instead of sorting elements,
     * we place numbers into "buckets"
     * where the index represents
     * their frequency.
     * <p>
     * Example:
     * <p>
     * bucket[3] → contains numbers
     * that appear 3 times.
     * <p>
     * By scanning buckets from
     * highest frequency to lowest,
     * we naturally obtain the
     * most frequent elements first.
     * <p>
     * Thought Process:
     * <p>
     * Step 1:
     * <p>
     * Count how many times each number
     * appears using a HashMap.
     * <p>
     * Step 2:
     * <p>
     * Create an array of lists
     * called buckets.
     * <p>
     * Each index represents
     * a frequency.
     * <p>
     * buckets[i] will store numbers
     * that appear exactly i times.
     * <p>
     * Step 3:
     * <p>
     * Place each number into
     * its corresponding bucket
     * based on its frequency.
     * <p>
     * Step 4:
     * <p>
     * Traverse the buckets
     * from the highest frequency
     * down to the lowest.
     * <p>
     * Collect numbers until
     * we have gathered k elements.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Build the frequency map.
     * <p>
     * Example:
     * <p>
     * nums = [1,1,1,2,2,3]
     * <p>
     * Frequency Map:
     * <p>
     * 1 → 3
     * 2 → 2
     * 3 → 1
     * <p>
     * Step 2:
     * <p>
     * Create buckets of size
     * nums.length + 1.
     * <p>
     * The extra slot exists because
     * frequency can be equal to
     * nums.length.
     * <p>
     * Step 3:
     * <p>
     * Place numbers into buckets
     * based on their frequency.
     * <p>
     * buckets[3] → [1]
     * buckets[2] → [2]
     * buckets[1] → [3]
     * <p>
     * Step 4:
     * <p>
     * Traverse buckets from the end
     * (highest frequency).
     * <p>
     * buckets[3] → add 1
     * buckets[2] → add 2
     * <p>
     * Stop once k elements
     * are collected.
     * <p>
     * Example 1:
     * <p>
     * nums = [1,1,1,2,2,3]
     * k = 2
     * <p>
     * Buckets:
     * <p>
     * bucket[3] → [1]
     * bucket[2] → [2]
     * bucket[1] → [3]
     * <p>
     * Walk backwards:
     * <p>
     * frequency 3 → add 1
     * frequency 2 → add 2
     * <p>
     * Output → [1,2]
     * <p>
     * Example 2:
     * <p>
     * nums = [1]
     * k = 1
     * <p>
     * bucket[1] → [1]
     * <p>
     * Output → [1]
     * <p>
     * Complexity:
     * <p>
     * Let n = number of elements.
     * <p>
     * Time Complexity:
     * <p>
     * O(n)
     * <p>
     * - O(n) to build the frequency map
     * - O(n) to distribute elements into buckets
     * - O(n) to scan buckets
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(n)
     * <p>
     * - HashMap stores frequencies
     * - Bucket array stores elements
     * <p>
     * Interview Takeaway:
     * <p>
     * When the range of possible values
     * (like frequencies) is known and small,
     * Bucket Sort can eliminate the need
     * for expensive sorting or heap operations.
     * <p>
     * This approach achieves linear time,
     * which satisfies the follow-up requirement
     * of being faster than O(n log n).
     */
    private static int[] topKFrequentOptimal(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Create Buckets
        // The maximum possible frequency is nums.length
        List<Integer>[] buckets = new List[nums.length + 1];

        for (int num : map.keySet()) {
            int frequency = map.get(num);
            if (buckets[frequency] == null) {
                buckets[frequency] = new ArrayList<>();
            }
            buckets[frequency].add(num);
        }

        // Collect the top K by walking backwards from the highest frequency
        int[] result = new int[k];
        int index = 0;
        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[index++] = num;
                    if (index == k) return result;
                }
            }
        }
        return result;
    }
}
