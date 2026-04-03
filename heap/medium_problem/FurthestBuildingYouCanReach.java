package heap.medium_problem;

import java.util.PriorityQueue;

/*
Leetcode 1642: Furthest Building You Can Reach

You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
While moving from building i to building i+1 (0-indexed),
If the current building's height is greater than or equal to the next building's height, you do not need a ladder or bricks.
If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] - h[i]) bricks.
Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.

Example 1:
Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
Output: 4
Explanation: Starting at building 0, you can follow these steps:
- Go to building 1 without using ladders nor bricks since 4 >= 2.
- Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
- Go to building 3 without using ladders nor bricks since 7 >= 6.
- Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
It is impossible to go beyond building 4 because you do not have any more bricks or ladders.

Example 2:
Input: heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
Output: 7

Example 3:
Input: heights = [14,3,19,3], bricks = 17, ladders = 0
Output: 3

Constraints:
            1 <= heights.length <= 10^5
            1 <= heights[i] <= 10^6
            0 <= bricks <= 10^9
            0 <= ladders <= heights.length
 */
public class FurthestBuildingYouCanReach {
    public static void main(String[] args) {
        int[] heights = {4, 2, 7, 6, 9, 14, 12};
        int bricks = 5;
        int ladders = 1;
        System.out.println(furthestBuildingBruteForce(heights, bricks, ladders, 0));
        System.out.println(furthestBuildingOptimal(heights, bricks, ladders));

        int[] heights1 = {4, 12, 2, 7, 3, 18, 20, 3, 19};
        int bricks1 = 10;
        int ladders1 = 2;
        System.out.println(furthestBuildingBruteForce(heights1, bricks1, ladders1, 0));
        System.out.println(furthestBuildingOptimal(heights1, bricks1, ladders1));

    }

    /**
     * <h2>THE "DECISION TREE" PATTERN (RECURSIVE BRUTE FORCE)</h2>
     *
     * <h3>THE PROBLEM:</h3>
     * Finding the furthest building by trying every possible combination of
     * bricks and ladders at every single upward climb.
     *
     * <h3>REAL-LIFE ANALOGY: The "Parallel Universes" Explorer</h3>
     * Imagine you are a traveler with a finite set of tools (Ladders and Bricks).
     * Every time you hit a mountain, you can't decide which tool is better.
     * So, you <b>clone yourself</b>. One "you" uses a ladder, and the other "you"
     * uses bricks. You both keep going until you run out of resources. At the end,
     * you look at which "you" made it the furthest.
     *
     *
     *
     * <h3>ALGORITHM EXPLAINED:</h3>
     * <b>Example:</b> heights = [4, 7], bricks = 5, ladders = 1
     * <ol>
     * <li><b>Step 1:</b> Calculate the climb. {@code 7 - 4 = 3}.</li>
     * <li><b>Branch A:</b> Use a ladder. New state: {@code ladders = 0, bricks = 5}. Proceed to next.</li>
     * <li><b>Branch B:</b> Use bricks. New state: {@code ladders = 1, bricks = 2}. Proceed to next.</li>
     * <li><b>Result:</b> Take the {@code Math.max()} distance reached by both branches.</li>
     * </ol>
     *
     * <h3>COMPLEXITY:</h3>
     * <ul>
     * <li><b>Time: O(2^N)</b> - In the worst case (every building is a climb),
     * we branch twice at every step. For N=10^5, this is mathematically impossible to finish.</li>
     * <li><b>Space: O(N)</b> - Due to the recursion stack depth.</li>
     * </ul>
     *
     * <h3>INTERVIEW TAKEAWAY:</h3>
     * Brute force is great for identifying the <b>Optimal Substructure</b> (the fact that
     * the best path depends on the best choice at the current step), but it fails because
     * of <b>Overlapping Subproblems</b>. It is the "first draft" that leads you to
     * realize you need a more efficient way to "regret" past decisions.
     */
    public static int furthestBuildingBruteForce(int[] heights, int bricks, int ladders, int index) {
        if (index == heights.length - 1) return index;

        int climb = heights[index + 1] - heights[index];

        if (climb <= 0) {
            return furthestBuildingBruteForce(heights, bricks, ladders, index + 1);
        }

        int resWithLadder = index;
        if (ladders > 0) {
            resWithLadder = furthestBuildingBruteForce(heights, bricks, ladders - 1, index + 1);
        }

        int resWithBricks = index;
        if (bricks >= climb) {
            resWithBricks = furthestBuildingBruteForce(heights, bricks - climb, ladders, index + 1);
        }

        return Math.max(resWithLadder, resWithBricks);
    }


    /**
     * <h2>THE "HINDSIGHT" PATTERN (GREEDY + PRIORITY QUEUE)</h2>
     *
     * <h3>THE PROBLEM:</h3>
     * We want to use our most powerful resource (Ladders) for the <b>largest</b> climbs 
     * and our limited resource (Bricks) for the <b>smallest</b> climbs.
     *
     * <h3>REAL-LIFE ANALOGY: The "Contractor's Budget"</h3>
     * You are building a path. Ladders are "Infinite Vouchers" (they cover any height), 
     * and Bricks are "Cash" (you pay by the inch). 
     * <br><br>
     * <b>The Strategy:</b> You use your Vouchers (Ladders) for the first few climbs you see. 
     * But you keep the receipts in a pile (Min-Heap). When you run out of Vouchers and hit 
     * a new climb, you look at your pile. If the new climb is bigger than the smallest 
     * receipt in your pile, you "regret" using a Voucher on that small climb. You pay 
     * Cash (Bricks) for that small one instead and <b>reclaim</b> the Voucher for the new, 
     * bigger climb.
     *
     *
     *
     * <h3>ALGORITHM EXPLAINED:</h3>
     * <b>Example:</b> heights = [1, 10, 11], bricks = 5, ladders = 1
     * <ol>
     * <li><b>Climb 1:</b> {@code 10 - 1 = 9}. Use a ladder. Heap: {@code [9]}.</li>
     * <li><b>Climb 2:</b> {@code 11 - 10 = 1}. We are out of ladders! </li>
     * <li><b>The Regret:</b> The smallest climb in our heap is 9. Is our current climb (1) 
     * smaller than 9? Yes. So we use <b>bricks</b> for the current climb (1).</li>
     * <li><b>Result:</b> We still have {@code 5 - 1 = 4} bricks left. Move to next.</li>
     * </ol>
     *
     * <h3>COMPLEXITY:</h3>
     * <ul>
     * <li><b>Time: O(N \log L)</b> - We iterate through N buildings. Each 
     * heap operation (offer/poll) takes \log L time, where L is the number of ladders.</li>
     * <li><b>Space: O(L)</b> - We only store at most L climbs in our PriorityQueue.</li>
     * </ul>
     *
     * <h3>INTERVIEW TAKEAWAY:</h3>
     * This is a <b>"Greedy with Regret"</b> problem. Whenever you see a problem where 
     * you have two resources and one is strictly better than the other, think: 
     * "Can I use the best resource now and trade it back later if I find a better 
     * spot for it?" The Heap is the tool that lets you "change your mind" efficiently.
     */
    private static int furthestBuildingOptimal(int[] heights, int bricks, int ladders) {
        int furthestBuilding = 0;
        int index = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        while (index < heights.length - 1) {
            int diff = heights[index + 1] - heights[index];

            if (diff <= 0) {
                index++;
                continue;
            }

            if (ladders > 0) {
                ladders--;
                minHeap.offer(diff);
                index++;
                continue;
            } else if (bricks > 0) {
                int minLadderDiff = minHeap.isEmpty() ? 0 : minHeap.peek();

                if (minHeap.isEmpty()) {
                    bricks -= diff;


                } else if (diff > minLadderDiff) {
                    bricks -= minHeap.poll();
                    minHeap.offer(diff);
                } else {
                    bricks -= diff;
                }
                if (bricks < 0) {
                    return index;
                }
                index++;
                continue;
            } else {
                return index;
            }
        }
        return index;
    }
}
