package greedy.medium_problems;

/*
Leetcode 55. Jump Game

You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array
represents your maximum jump length at that position. Return true if you can reach the last index, or false otherwise.

Example 1:
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

Example 2:
Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible
             to reach the last index.

Constraints:
            1 <= nums.length <= 10^4
            0 <= nums[i] <= 10^5
 */

/*
INTERVIEW QUESTIONS TO CONSIDER:

Why does Brute Force fail?
Answer: "Overlapping Subproblems." You might reach index 5 through ten different jump combinations, and Brute Force
re-calculates the path from index 5 ten different times.

What is the "Villain" in this problem?
Answer: The number 0. Zeros are the only things that can stop your horizon from moving forward.

Can we solve this by walking backwards?
Answer: Yes! You can set the "Goal" at the last index and move the goal backwards whenever you find a stone that can reach it.
 */
public class JumpGame {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 4};
        System.out.println(canjumpBruteForce(arr));
        System.out.println(canJumpOptimal(arr));
        int[] arr1 = {2, 3, 1, 1, 4};
        System.out.println(canjumpBruteForce(arr1));
        System.out.println(canJumpOptimal(arr1));
        int[] arr2 = {3, 2, 1, 0, 4};
        System.out.println(canjumpBruteForce(arr2));
        System.out.println(canJumpOptimal(arr2));
    }

    /*
    BRUTE FORCE METHOD: RECURSIVE EXPLORATION (RECURSION)

    THE INTUITION:
    The brute force approach is the "curious explorer" strategy. When you stand on a stone that says "3," you don't know
    which jump is the best one. Maybe jumping 1 step is better than jumping 3 steps because the 3rd step might land you
    on a 0. Since you can't predict the future, you branch out into multiple parallel universes. You try every single
    possible jump size allowed and see if any of them lead to the treasure at the end of the path.

    HOW THE ALGORITHM WORKS (STEP-BY-STEP):
    START: Begin at index 0.
    BASE CASE: Check if your current position is at or beyond the last stone. If yes, you've won! Return TRUE.
    THE CHOICES: Look at the number on your current stone. This is your 'maxJumpAllowed'.
    THE BRANCHING: Use a loop to try every possible jump from 1 up to 'maxJumpAllowed'.
    RECURSION: For every jump you try, start a new search from that new landing spot.
    THE WIN CONDITION: If even one of those jumps eventually finds a path to the end, the whole method returns TRUE.
    THE DEAD END: If you try every jump and none work, return FALSE.

    CODE EXPLANATION:
    canReach(currentIndex, nums): This helper keeps track of where you are standing right now.
    nextStep loop: This is the "Decision Engine." It generates a new branch for every possible jump size.
    currentIndex + nextStep: This is the actual "jump" action, moving you forward in the array.

    COMPLEXITY:
    Time: O(2^n) - This is extremely slow because the number of paths grows exponentially. For an array of size 100, you
    could be checking trillions of paths.
    Space: O(n) - The "Recursion Stack." The computer has to remember your path so it can "backtrack" if it hits a 0.
     */
    private static boolean canjumpBruteForce(int[] nums) {
        return canReach(0, nums);
    }

    private static boolean canReach(int currentIndex, int[] nums) {
        // Base Case: If we reached the last index
        if (currentIndex >= nums.length - 1) {
            return true;
        }

        int maxJumpAllowed = nums[currentIndex];

        // Try every possible jump from 1 step up to maxJumpAllowed
        for (int nextStep = 1; nextStep <= maxJumpAllowed; nextStep++) {
            // If this specific jump path leads to the end, return true
            if (canReach(currentIndex + nextStep, nums)) {
                return true;
            }
        }

        // If no jumps from this position work
        return false;
    }

    /*
    THE INTUITION:
    Instead of trying every path, think like a scout looking through binoculars. You don't care exactly which jumps you
    took to get to your current spot. You only care about the "Farthest Reachable Point" (the Horizon). As you walk
    forward stone by stone, you keep updating how far your binoculars can see. As long as you are standing within your
    "safety zone" (the area you've already proven you can reach), you just keep pushing the horizon forward.

    HOW THE ALGORITHM WORKS (STEP-BY-STEP):
    INITIALIZE: Start your 'maxIndex' (horizon) at 0.
    THE WALK: Walk through the array stone by stone (from index 0 to N-1).
    THE GUARDRAIL: If you ever find yourself at an index 'i' that is greater than your 'maxIndex', it means you've walked
    into "No Man's Land." You couldn't have possibly reached this stone. Return FALSE.
    THE UPDATE: At every stone, calculate how far you could go from there (i + nums[i]). If this is further than your
    current horizon, update 'maxIndex'.
    THE SUCCESS: If you finish the loop without getting stuck, it means the last stone was within your reachable horizon.
    Return TRUE.

    CODE EXPLANATION:
    maxIndex: This variable represents the boundary of your "Safety Zone."
    if (i > maxIndex): This is the "Game Over" check. It triggers if you hit a gap you can't jump across (like a 0 that
    blocks the way).
    Math.max(maxIndex, i + nums[i]): This is the Greedy choice. You always want to know the absolute best potential reach
    available to you.

    COMPLEXITY:
    Time: O(n) - You walk through the array exactly once. It is incredibly fast.
    Space: O(1) - You only need to remember one number (the maxIndex).

    THE PATTERN TAKEAWAY:
    This is the "Reachable Range" pattern. When a problem asks if a destination is "possible" and involves overlapping
    choices, look for a way to track the boundary of possibility (the Range) instead of individual paths.
     */
    private static boolean canJumpOptimal(int[] nums) {
        int maxIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxIndex) return false;

            maxIndex = Math.max(maxIndex, i + nums[i]);
        }
        return true;
    }

}

