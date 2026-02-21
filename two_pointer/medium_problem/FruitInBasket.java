package two_pointer.medium_problem;

import java.util.HashSet;
import java.util.Set;

/*
Problem: Fruit Into Baskets

There is only one row of fruit trees on the farm, oriented left to right. An integer array called fruits represents the trees,
where fruits[i] denotes the kind of fruit produced by the ith tree.

The goal is to gather as much fruit as possible, adhering to the owner's stringent rules:
1) There are two baskets available, and each basket can only contain one kind of fruit.
The quantity of fruit each basket can contain is unlimited.
2) Start at any tree, but as you proceed to the right, select exactly one fruit from each tree, including the starting tree.
One of the baskets must hold the harvested fruits.
3) Once reaching a tree with fruit that cannot fit into any basket, stop.

Return the maximum number of fruits that can be picked.

Example 1
Input : fruits = [1, 2, 1]
Output : 3
Explanation : We will start from first tree.
The first tree produces the fruit of kind '1' and we will put that in the first basket.
The second tree produces the fruit of kind '2' and we will put that in the second basket.
The third tree produces the fruit of kind '1' and we have first basket that is already holding fruit of kind '1'.
So we will put it in first basket.
Hence we were able to collect total of 3 fruits.

Example 2
Input : fruits = [1, 2, 3, 2, 2]
Output : 4
Explanation : we will start from second tree.
The first basket contains fruits from second , fourth and fifth.
The second basket will contain fruit from third tree.
Hence we collected total of 4 fruits.


Constraints:
            1 <= fruits.length <= 10^5
            0 <= fruits[i] < fruits.length
 */
public class FruitInBasket {

    public static void main(String[] args) {
        System.out.println(findMaxFruitBruteForce(new int[]{1, 2, 1}));
        System.out.println(findMaxFruitBruteForce(new int[]{1, 2, 3, 2, 2}));
        System.out.println(findMaxFruitBruteForce(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}));
    }

    /**
     * Method: findMaxFruitBruteForce(int[] fruits)
     * <p>
     * What this method does:
     * Finds the maximum number of fruits that can be collected
     * while using at most two baskets, where each basket can hold
     * only one type of fruit.
     * <p>
     * Core Idea:
     * Generate every possible contiguous subarray.
     * For each starting position, keep adding fruits to baskets
     * as long as we have at most two distinct fruit types.
     * If a third type appears, stop expanding that window.
     * <p>
     * Thought Process:
     * <p>
     * The rules translate to:
     * - We must pick fruits continuously (a subarray).
     * - We can store at most 2 distinct fruit types.
     * <p>
     * So the most straightforward approach is:
     * <p>
     * 1. Choose a starting tree.
     * 2. Expand to the right.
     * 3. Keep track of distinct fruit types using a Set.
     * 4. If distinct types exceed 2 → stop.
     * 5. Update the maximum length found.
     * <p>
     * This guarantees correctness because we examine
     * every valid starting position.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int answer = 0;
     * Stores the maximum number of fruits collected.
     * <p>
     * Outer Loop:
     * for (int i = 0; i < fruits.length; i++)
     * <p>
     * Choose every possible starting tree.
     * <p>
     * For each starting index:
     * Create a new HashSet to track distinct fruit types.
     * <p>
     * Set<Integer> set = new HashSet<>();
     * <p>
     * Inner Loop:
     * for (int j = i; j < fruits.length; j++)
     * <p>
     * Expand window from index i to j.
     * <p>
     * set.add(fruits[j]);
     * Add the current fruit type into the basket tracking set.
     * <p>
     * If set.size() <= 2:
     * We are still within the allowed limit of 2 fruit types.
     * Update answer:
     * <p>
     * answer = Math.max(answer, j - i + 1);
     * <p>
     * Else:
     * If more than 2 distinct fruit types appear,
     * break the loop because this window is no longer valid.
     * <p>
     * Example:
     * <p>
     * fruits = [1, 2, 3, 2, 2]
     * <p>
     * Starting at index 1:
     * Window grows: [2] → [2,3] → [2,3,2] → [2,3,2,2]
     * Still only 2 types.
     * <p>
     * When a third type appears,
     * expansion stops.
     * <p>
     * Complexity:
     * <p>
     * Outer loop runs n times.
     * Inner loop runs up to n times.
     * <p>
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     * <p>
     * Note:
     * The HashSet holds at most 3 elements at any time,
     * so space usage is constant relative to input size.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is the brute force solution.
     * It works but is inefficient for large inputs.
     * <p>
     * The key observation for optimization:
     * This is essentially:
     * <p>
     * "Longest subarray with at most 2 distinct elements."
     * <p>
     * That pattern screams Sliding Window with HashMap.
     * <p>
     * Optimized solution runs in O(n).
     */
    private static int findMaxFruitBruteForce(int[] fruits) {

        int answer = 0;
        for (int i = 0; i < fruits.length; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = i; j < fruits.length; j++) {
                set.add(fruits[j]);
                if (set.size() <= 2)
                    answer = Math.max(answer, j - i + 1);
                else break;
            }
        }
        return answer;
    }
}
