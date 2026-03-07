package heap.easy_problem;

import java.util.PriorityQueue;

/*
Leetcode 1046. Last Stone Weight

You are given an array of integers stones where stones[i] is the weight of the ith stone.
We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
If x == y, both stones are destroyed, and
If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
At the end of the game, there is at most one stone left.
Return the weight of the last remaining stone. If there are no stones left, return 0.

Example 1:
Input: stones = [2,7,4,1,8,1]
Output: 1
Explanation:
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.

Example 2:
Input: stones = [1]
Output: 1

Constraints:
            1 <= stones.length <= 30
            1 <= stones[i] <= 1000
 */
public class LastStoneWeight {

    public static void main(String[] args) {
        int[] stones = {2, 7, 4, 1, 8, 1};
        System.out.println(lastStoneWeight(stones));

    }

    /**
     * lastStoneWeight(int[] stones)
     * <p>
     * What this method does:
     * <p>
     * Simulates the stone smashing game
     * and returns the weight of the last
     * remaining stone.
     * <p>
     * If all stones destroy each other,
     * the method returns 0.
     * <p>
     * Core Idea:
     * <p>
     * At every turn we must pick
     * the two heaviest stones.
     * <p>
     * The most efficient structure
     * for repeatedly extracting
     * the largest element is a
     * Max Heap.
     * <p>
     * A Max Heap always keeps
     * the largest value at the root,
     * allowing us to retrieve it
     * in constant time.
     * <p>
     * Thought Process:
     * <p>
     * Instead of sorting the array
     * after every smash,
     * we insert all stones into
     * a Max Heap.
     * <p>
     * This allows us to repeatedly
     * remove the two largest stones
     * efficiently.
     * <p>
     * If the stones have equal weight:
     * <p>
     * Both are destroyed.
     * <p>
     * If they have different weights:
     * <p>
     * The heavier stone survives
     * with reduced weight:
     * <p>
     * newWeight = y - x
     * <p>
     * That new stone is placed
     * back into the heap.
     * <p>
     * This process continues until
     * at most one stone remains.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create a Max Heap.
     * <p>
     * Java's PriorityQueue is
     * normally a Min Heap,
     * so we reverse the comparison
     * using:
     * <p>
     * (a, b) -> b - a
     * <p>
     * Step 2:
     * <p>
     * Insert all stones into
     * the heap.
     * <p>
     * Step 3:
     * <p>
     * While there are at least
     * two stones:
     * <p>
     * - Remove the heaviest stone (y)
     * - Remove the second heaviest (x)
     * <p>
     * Step 4:
     * <p>
     * If the stones are different:
     * <p>
     * The remaining weight is:
     * <p>
     * y - x
     * <p>
     * Insert this new stone
     * back into the heap.
     * <p>
     * Step 5:
     * <p>
     * When the loop finishes,
     * either:
     * <p>
     * - One stone remains
     * - No stones remain
     * <p>
     * Step 6:
     * <p>
     * If the heap is empty,
     * return 0.
     * <p>
     * Otherwise return
     * the last stone's weight.
     * <p>
     * Example 1:
     * <p>
     * stones = [2,7,4,1,8,1]
     * <p>
     * Heap initially:
     * <p>
     * [8,7,4,2,1,1]
     * <p>
     * Smash:
     * <p>
     * 8 and 7 → 1
     * <p>
     * New heap:
     * <p>
     * [4,2,1,1,1]
     * <p>
     * Smash:
     * <p>
     * 4 and 2 → 2
     * <p>
     * New heap:
     * <p>
     * [2,1,1,1]
     * <p>
     * Smash:
     * <p>
     * 2 and 1 → 1
     * <p>
     * New heap:
     * <p>
     * [1,1,1]
     * <p>
     * Smash:
     * <p>
     * 1 and 1 → destroyed
     * <p>
     * Remaining:
     * <p>
     * [1]
     * <p>
     * Output → 1
     * <p>
     * Example 2:
     * <p>
     * stones = [1]
     * <p>
     * Only one stone exists.
     * <p>
     * Output → 1
     * <p>
     * Complexity:
     * <p>
     * Let n = number of stones.
     * <p>
     * Time Complexity:
     * <p>
     * O(n log n)
     * <p>
     * - Building the heap takes O(n log n)
     * - Each smash involves heap operations
     * that take O(log n)
     * <p>
     * Space Complexity:
     * <p>
     * O(n)
     * <p>
     * The heap stores up to n stones.
     * <p>
     * Interview Takeaway:
     * <p>
     * Whenever a problem repeatedly
     * requires selecting the
     * largest (or smallest) elements,
     * a Heap is usually the
     * optimal structure.
     * <p>
     * In this problem, the Max Heap
     * models the game arena where
     * the heaviest stones always
     * fight first.
     */

    private static int lastStoneWeight(int[] stones) {
        //Create a Max - Heap to always grab the "heaviest" stones
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int stone : stones) {
            maxHeap.add(stone);
        }
        //Smash stones as long as there are at least 2 in the arena
        while (maxHeap.size() > 1) {
            int weightY = maxHeap.poll(); // Heaviest
            int weightX = maxHeap.isEmpty() ? 0 : maxHeap.poll(); // Second heaviest

            if (weightX != weightY) {
                maxHeap.add(weightY - weightX); // Return the "damaged" survivor
            }
        }
        //If the arena is empty, return 0; otherwise, return the last survivor
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }
}
