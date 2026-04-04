package greedy.easy_problems;

import java.util.Arrays;

/*
Leetcode 455. Assign Cookies

Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie.
Each child i has a greed factor g[i], which is the minimum size of a cookie that the child will be content with; and each cookie
j has a size s[j]. If s[j] >= g[i], we can assign the cookie j to the child i, and the child i will be content. Your goal is to
maximize the number of your content children and output the maximum number.

Example 1:
Input: g = [1,2,3], s = [1,1]
Output: 1
Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3.
And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
You need to output 1.

Example 2:
Input: g = [1,2], s = [1,2,3]
Output: 2
Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
You have 3 cookies and their sizes are big enough to gratify all of the children,
You need to output 2.

Constraints:
            1 <= g.length <= 3 * 10^4
            0 <= s.length <= 3 * 10^4
            1 <= g[i], s[j] <= 2^31 - 1

Note: This question is the same as 2410: Maximum Matching of Players With Trainers.
 */
public class AssignCookies {

    public static void main(String[] args) {
        int[] g = {1, 2, 3};
        int[] s = {1, 1};
        System.out.println(findContentChildrenBruteForce(g, s));
        System.out.println(findContentChildrenOptimal(g, s));
    }


    /**
     * findContentChildrenBruteForce(int[] g, int[] s)
     * <p>
     * What this method does:
     * <p>
     * Finds the maximum number of children
     * that can be satisfied by trying
     * all possible cookie assignments.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * For each child,
     * try every cookie and assign
     * the first valid unused one.
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * Each child needs:
     * <p>
     * cookie size ≥ greed factor
     * <p>
     * So we:
     * <p>
     * → Iterate over each child
     * → For each child, scan all cookies
     * → Assign the first unused cookie
     * that satisfies the child
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create a boolean array:
     * <p>
     * usedCookie[]
     * <p>
     * → Tracks which cookies are already used
     * <p>
     * <p>
     * Step 2:
     * <p>
     * Loop over each child.
     * <p>
     * <p>
     * Step 3:
     * <p>
     * For each child,
     * check all cookies:
     * <p>
     * If:
     * - cookie is not used
     * - cookie size ≥ child greed
     * <p>
     * → Assign cookie
     * → Mark it as used
     * → Increase count
     * → Break (move to next child)
     * <p>
     * <p>
     * Example:
     * <p>
     * g = [1,2,3]
     * s = [1,1]
     * <p>
     * Child 1 → gets cookie 1
     * Child 2 → no valid cookie
     * Child 3 → no valid cookie
     * <p>
     * Result = 1
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(n * m)
     * <p>
     * n = number of children
     * m = number of cookies
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(m)
     * <p>
     * → usedCookie array
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This approach is simple
     * but inefficient due to
     * nested iteration.
     */
    private static int findContentChildrenBruteForce(int[] g, int[] s) {

        int count = 0;
        boolean[] usedCookie = new boolean[s.length];

        // Try to satisfy each child one by one
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < s.length; j++) {
                // If cookie isn't used and is big enough
                if (!usedCookie[j] && s[j] >= g[i]) {
                    usedCookie[j] = true; // Mark as used
                    count++;
                    break; // Move to the next child
                }
            }
        }
        return count;
    }


    /**
     * findContentChildrenOptimal(int[] g, int[] s)
     * <p>
     * What this method does:
     * <p>
     * Returns the maximum number of
     * satisfied children using an
     * optimal greedy approach.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * Always satisfy the least greedy child
     * with the smallest available cookie.
     * <p>
     * <p>
     * Why this works:
     * <p>
     * - Small cookies can only satisfy small greed
     * - Large cookies can satisfy both
     * <p>
     * So we:
     * <p>
     * → Use small cookies first
     * → Save large cookies for greedy children
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * Sort both arrays:
     * <p>
     * g → greed (ascending)
     * s → cookies (ascending)
     * <p>
     * Then match them using two pointers.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Sort both arrays.
     * <p>
     * <p>
     * Step 2:
     * <p>
     * Initialize:
     * <p>
     * childIdx = 0
     * cookieIdx = 0
     * <p>
     * <p>
     * Step 3:
     * <p>
     * While both pointers are valid:
     * <p>
     * If current cookie ≥ current greed:
     * <p>
     * → Child is satisfied
     * → Move to next child
     * <p>
     * <p>
     * Always:
     * <p>
     * → Move to next cookie
     * <p>
     * <p>
     * Why always move cookie?
     * <p>
     * - If cookie too small → discard it
     * - If used → move forward
     * <p>
     * <p>
     * Step 4:
     * <p>
     * childIdx gives number of
     * satisfied children.
     * <p>
     * <p>
     * Example:
     * <p>
     * g = [1,2]
     * s = [1,2,3]
     * <p>
     * Sorted:
     * <p>
     * g = [1,2]
     * s = [1,2,3]
     * <p>
     * Match:
     * <p>
     * child 1 ← cookie 1
     * child 2 ← cookie 2
     * <p>
     * Result = 2
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(n log n + m log m)
     * <p>
     * → Sorting
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a classic greedy pattern:
     * <p>
     * → Sort + Two Pointers
     * <p>
     * <p>
     * Golden Rule:
     * <p>
     * Always try to satisfy the smallest
     * requirement first to maximize total matches.
     */
    private static int findContentChildrenOptimal(int[] g, int[] s) {
        Arrays.sort(s);
        Arrays.sort(g);

        int childIdx = 0;
        int cookieIdx = 0;

        // We only keep going as long as we have both children left and cookies left
        while (childIdx < g.length && cookieIdx < s.length) {
            // If the current cookie can satisfy the current child
            if (s[cookieIdx] >= g[childIdx]) {
                childIdx++; // Move to the next child (this one is satisfied)
            }
            // Always move to the next cookie, whether it was used or was too small
            cookieIdx++;
        }

        // The number of satisfied children is exactly childIdx
        return childIdx;
    }
}
