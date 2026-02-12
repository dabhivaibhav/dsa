package collection.stack;

import java.util.Arrays;
import java.util.Stack;

/*
Leetcode 735. Asteroid Collision

We are given an array asteroids of integers representing asteroids in a row. The indices of the asteroid in the array
represent their relative position in space.
For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right,
negative meaning left). Each asteroid moves at the same speed.
Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode.
If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

Example 1:
Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.

Example 2:
Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.

Example 3:
Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.

Example 4:
Input: asteroids = [3,5,-6,2,-1,4]
Output: [-6,2,4]
Explanation: The asteroid -6 makes the asteroid 3 and 5 explode, and then continues going left. On the other side, the asteroid 2 makes the asteroid -1 explode and then continues going right, without reaching asteroid 4.

Constraints:
            2 <= asteroids.length <= 10^4
            -1000 <= asteroids[i] <= 1000
            asteroids[i] != 0
 */
public class AsteroidCollision {

    public static void main(String[] args) {

        int[] asteroids = {5, 10, -5};
        System.out.println(Arrays.toString(asteroidCollisionBruteForce(asteroids)));

        asteroids = new int[]{8, -8};
        System.out.println(Arrays.toString(asteroidCollisionBruteForce(asteroids)));

        asteroids = new int[]{-2, -1, 1, 2};
        System.out.println(Arrays.toString(asteroidCollisionBruteForce(asteroids)));

        asteroids = new int[]{2, 5, 8, -10};
        System.out.println(Arrays.toString(asteroidCollisionBruteForce(asteroids)));

        asteroids = new int[]{-2, -2, 5, -5};
        System.out.println(Arrays.toString(asteroidCollisionBruteForce(asteroids)));

    }

    /*
    Thought Process:

    Initially, I carefully understood the problem requirements.
    The indices of the array represent the relative positions of the asteroids in space.
    The absolute value of each element represents the size of the asteroid,
    and the sign represents the direction of movement:
    positive means moving to the right, and negative means moving to the left.

    At first, I felt that moving strictly from left to right might not be ideal.
    I thought about a scenario where the first two asteroids are moving right,
    and the third asteroid is moving left and is larger than both.
    I assumed it would be difficult to correctly track such chain collisions.

    Because of this, I initially tried building a solution that iterated from right to left.
    That approach worked for a few test cases, but it quickly became messy.
    The logic required multiple nested if-else conditions,
    and handling edge cases became complicated and error-prone.

    At that point, I realized that this problem is really about
    handling interactions between the current element and the most recent unresolved element.
    That insight naturally led me to think about using a stack,
    since a stack is ideal for tracking previous elements and resolving conflicts.

    I then confirmed this intuition with AI guidance,
    which suggested the same stack-based approach.
    The key insight was that I should still process the asteroids from left to right,
    but use a stack to represent the asteroids that have survived so far.
    This is very similar to patterns like Next Greater Element,
    where we resolve conditions using the most recent elements first.

    The final idea became very clear:
    - Push all right-moving asteroids onto the stack.
    - When a left-moving asteroid appears, it can only collide with
      right-moving asteroids already in the stack.
    - Compare the current asteroid with the top of the stack:
      - If the current asteroid is larger, pop the stack and continue checking.
      - If both are the same size, both explode.
      - If the stack top is larger, the current asteroid explodes.
    - If the current asteroid survives all collisions, push it onto the stack.

    This approach simplified the logic significantly,
    handled chain collisions naturally,
    and resulted in a clean, efficient, and interview-ready solution.
    */

    /**
     * asteroidCollisionBruteForce
     * <p>
     * What it does:
     * Simulates the collision of asteroids moving in a straight line and
     * returns the final state of the asteroids after all possible collisions
     * have occurred.
     * <p>
     * Each asteroid has:
     * - A size (absolute value)
     * - A direction (positive means moving right, negative means moving left)
     * <p>
     * Core idea:
     * A collision can only happen when:
     * - One asteroid is moving right
     * - Another asteroid coming later is moving left
     * <p>
     * Asteroids moving in the same direction will never collide.
     * <p>
     * Why a stack is the right data structure:
     * The stack represents the asteroids that have already survived so far.
     * Each new asteroid is compared only with the most recent survivor
     * because that is the first one it can possibly collide with.
     * <p>
     * This makes the problem a classic "process incoming elements
     * and resolve conflicts with previous ones" scenario,
     * which is exactly what a stack is good at.
     * <p>
     * Explanation of the approach step by step:
     * <p>
     * - Initialize an empty stack to store surviving asteroids.
     * - Traverse the input array from left to right.
     * - For each asteroid:
     * - Assume initially that it has not exploded.
     * - While a collision is possible:
     * - Collision condition:
     * current asteroid is moving left (negative)
     * AND top of the stack is moving right (positive)
     * <p>
     * Collision resolution logic:
     * - If the current asteroid is larger:
     * - The stack top explodes and is popped.
     * - The current asteroid may still collide again, so continue checking.
     * <p>
     * - If both asteroids are the same size:
     * - Both explode.
     * - Pop the stack top.
     * - Mark the current asteroid as exploded.
     * - Stop further checks.
     * <p>
     * - If the stack top is larger:
     * - The current asteroid explodes.
     * - Stop further checks.
     * <p>
     * - If the current asteroid survives all possible collisions:
     * - Push it onto the stack.
     * <p>
     * Final step:
     * - Convert the stack into an array.
     * - Since stack pops elements in reverse order,
     * fill the result array from the end.
     * <p>
     * Example walkthrough:
     * asteroids = [5, 10, -5]
     * <p>
     * - Push 5
     * - Push 10
     * - -5 collides with 10
     * size 5 < 10, so -5 explodes
     * - Stack remains [5, 10]
     * <p>
     * Example with chain collision:
     * asteroids = [10, 2, -5]
     * <p>
     * - Push 10
     * - Push 2
     * - -5 collides with 2, 2 explodes
     * - -5 collides with 10, -5 explodes
     * - Stack remains [10]
     * <p>
     * Important details:
     * - Multiple collisions can happen for a single asteroid.
     * - The while loop handles chain reactions correctly.
     * - Only opposite directions can collide.
     * - Absolute values are used for size comparison.
     * <p>
     * Complexity:
     * Time: O(n)
     * Each asteroid is pushed and popped at most once.
     * <p>
     * Space: O(n)
     * Stack stores surviving asteroids.
     * <p>
     * Interview takeaway:
     * This problem is a perfect example of using a stack
     * to resolve sequential conflicts.
     * The key insight is identifying when collisions are possible
     * and handling chain reactions correctly.
     * Once the collision condition is clear,
     * the implementation becomes straightforward.
     */
    private static int[] asteroidCollisionBruteForce(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int ast : asteroids) {
            // Current is moving Left (< 0), AND Top of the stack is moving Right (> 0)
            boolean exploded = false;
            while (!stack.isEmpty() && ast < 0 && stack.peek() > 0) {
                if (Math.abs(ast) > stack.peek()) {
                    stack.pop(); // Stack top explodes, Bully keeps going
                } else if (Math.abs(ast) == stack.peek()) {
                    stack.pop(); // Both explode
                    exploded = true;
                    break;
                } else {
                    exploded = true; // Bully explodes
                    break;
                }
            }
            // If the current asteroid didn't explode, it joins the survivors
            if (!exploded) {
                stack.push(ast);
            }
        }
        //Converting from stack to array
        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
