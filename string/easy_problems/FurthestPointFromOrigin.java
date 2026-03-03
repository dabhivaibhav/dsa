package string.easy_problems;

/*
Leetcode 2833: Furthest Point From Origin

You are given a string moves of length n consisting only of characters 'L', 'R', and '_'.
The string represents your movement on a number line starting from the origin 0.
In the ith move, you can choose one of the following directions:
move to the left if moves[i] = 'L' or moves[i] = '_'
move to the right if moves[i] = 'R' or moves[i] = '_'
Return the distance from the origin of the furthest point you can get to after n moves.

Example 1:
Input: moves = "L_RL__R"
Output: 3
Explanation: The furthest point we can reach from the origin 0 is point -3 through the following sequence of moves "LLRLLLR".

Example 2:
Input: moves = "_R__LL_"
Output: 5
Explanation: The furthest point we can reach from the origin 0 is point -5 through the following sequence of moves "LRLLLLL".

Example 3:
Input: moves = "_______"
Output: 7
Explanation: The furthest point we can reach from the origin 0 is point 7 through the following sequence of moves "RRRRRRR".

Constraints:
            1 <= moves.length == n <= 50
            moves consists only of characters 'L', 'R' and '_'.
 */
public class FurthestPointFromOrigin {

    public static void main(String[] args) {
        String moves = "L_RL__R";
        System.out.println(furthestPointFromOrigin(moves));
        moves = "_R__LL_";
        System.out.println(furthestPointFromOrigin(moves));
        moves = "_______";
        System.out.println(furthestPointFromOrigin(moves));
    }

    /**
     * furthestPointFromOrigin(String moves)
     * <p>
     * What this method does:
     * <p>
     * Calculates the maximum possible distance from the origin (0)
     * after performing all moves in the string.
     * <p>
     * The string contains:
     * <p>
     * 'L' → move left (-1)
     * 'R' → move right (+1)
     * '_' → flexible move (can be either left or right)
     * <p>
     * Core Idea:
     * <p>
     * First compute the net displacement caused by fixed moves
     * ('L' and 'R').
     * <p>
     * Then add all flexible moves ('_') in the direction
     * that maximizes the absolute distance.
     * <p>
     * Instead of branching logic,
     * we observe a mathematical shortcut:
     * <p>
     * Final distance = |net fixed movement| + number of blanks
     * <p>
     * Thought Process:
     * <p>
     * Let:
     * <p>
     * counter → net displacement from 'L' and 'R'
     * blankSpace → count of '_' characters
     * <p>
     * Every 'L' reduces counter by 1.
     * Every 'R' increases counter by 1.
     * <p>
     * After processing all fixed moves,
     * we are at position = counter.
     * <p>
     * To maximize distance from origin,
     * we push every '_' in the same direction
     * as the current displacement.
     * <p>
     * If counter is positive → move right.
     * If counter is negative → move left.
     * If counter is zero → choose any direction.
     * <p>
     * In all cases,
     * <p>
     * maximum distance = |counter| + blankSpace
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Initialize counters
     * <p>
     * counter = 0
     * blankSpace = 0
     * <p>
     * Step 2: Traverse the string
     * <p>
     * For each character c:
     * <p>
     * if c == 'L' → counter--
     * else if c == 'R' → counter++
     * else → blankSpace++
     * <p>
     * Step 3: Return final result
     * <p>
     * return Math.abs(counter) + blankSpace
     * <p>
     * This directly applies the mathematical insight.
     * <p>
     * Example 1:
     * <p>
     * moves = "L_RL__R"
     * <p>
     * Fixed moves result in counter = 0
     * blankSpace = 3
     * <p>
     * distance = |0| + 3 = 3
     * <p>
     * Example 2:
     * <p>
     * moves = "_R__LL_"
     * <p>
     * Fixed moves:
     * R → +1
     * L → -1
     * L → -1
     * <p>
     * counter = -1
     * blankSpace = 4
     * <p>
     * distance = |−1| + 4 = 5
     * <p>
     * Example 3:
     * <p>
     * moves = "_______"
     * <p>
     * counter = 0
     * blankSpace = 7
     * <p>
     * distance = 0 + 7 = 7
     * <p>
     * Complexity:
     * <p>
     * Single pass through the string.
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(1
     * <p>
     * Interview Takeaway:
     * <p>
     * The key insight is recognizing that
     * flexible moves always increase the maximum distance
     * regardless of direction.
     * <p>
     * Instead of conditionally adding blanks,
     * we can compress the logic into a single formula.
     * <p>
     * This is a classic greedy observation:
     * maximize magnitude by committing fully
     * to one direction.
     */
    private static int furthestPointFromOrigin(String moves) {
        int counter = 0;
        int blankSpace = 0;
        for (char c : moves.toCharArray()) {
            if (c == 'L') counter--;
            else if (c == 'R') counter++;
            else blankSpace++;
        }
        return Math.abs(counter) + blankSpace;
    }
}
