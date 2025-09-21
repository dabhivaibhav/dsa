package binarysearch.BSOnAnswers;

/*
Painter's Partition
You are given A painters and an array C of N integers where C[i] denotes the length of the ith board. Each painter takes
B units of time to paint 1 unit of board. You must assign boards to painters such that:
Each painter paints only contiguous segments of boards.
No board can be split between painters.
The goal is to minimize the time to paint all boards.
Return the minimum time required to paint all boards modulo 10000003.

Examples:
Input: A = 2, B = 5, C = [1, 10]
Output: 50
Explanation:
Painter 1 paints board 0 (length = 1), time = 5
Painter 2 paints board 1 (length = 10), time = 50
Max time = 50
Return 50 % 10000003 = 50

Input: A = 10, B = 1, C = [1, 8, 11, 3]
Output: 11
Explanation:
Assign each board to a different painter
Max time = max(1, 8, 11, 3) = 11
Return 11 % 10000003 = 11

Constraints:
            1 <= A <= 1000
            1 <= B <= 10^6
            1 <= N <= 10^5
            1 <= C[i] <= 10^6
 */
public class PaintersPartition {

    public static void main(String[] args) {
        int[] C = {1, 10};
        int A = 2;
        int B = 5;
        int[] C1 = {1, 8, 11, 3};
        int A1 = 10;
        int B1 = 1;

        System.out.println(paintersPartitionBruteforce(C, A, B));
        System.out.println(paintersPartitionBruteforce(C1, A1, B1));
        System.out.println(paintersPartitionOptimal(C, A, B));
        System.out.println(paintersPartitionOptimal(C1, A1, B1));
    }

    /**
     * What it does:
     * Brute force solution to the Painter’s Partition problem. Finds the minimum time required
     * to paint all boards using A painters, where each painter paints contiguous boards, and
     * no board is split. Time is calculated as (capacity * B) modulo 10000003, where B is the
     * time to paint one unit of board.
     * <p>
     * How it works:
     * - The feasible capacity range is determined:
     * low = max(c): the largest board length (must be painted by someone).
     * high = sum(c): all boards painted by one painter.
     * - Iterate through every candidate capacity i from low to high.
     * - For each i, call canAssignPainter to check if the boards can be divided among
     * ≤ A painters without exceeding i units per painter.
     * - The first feasible i is the minimal capacity; multiply by B and return modulo 10000003.
     * <p>
     * Intuition:
     * - Someone must paint the biggest board (lower bound).
     * - One painter could paint everything (upper bound).
     * - By testing each possible capacity incrementally, the first feasible capacity is
     * guaranteed to be the minimal maximum load.
     * <p>
     * Example:
     * c = [10, 20, 30, 40], a = 2, b = 1
     * - low = 40, high = 100.
     * - Try i = 40 → not feasible.
     * - Try i = 60 → feasible (partition [10,20,30] and [40]).
     * - Answer = 60 * 1 % 10000003 = 60.
     * <p>
     * Time Complexity:
     * - O((high - low) * n) ≈ O(sum(c) * n) in worst case.
     * - Not feasible for large inputs (sum(c) can be up to 10^11).
     * <p>
     * Space Complexity:
     * - O(1) extra space.
     */

    private static int paintersPartitionBruteforce(int[] c, int a, int b) {

        if (c == null || c.length == 0 || a > c.length) return -1;

        int low = Integer.MIN_VALUE;
        int high = 0;
        for (int num : c) {
            low = Math.max(low, num);
            high += num;
        }

        int result = -1;

        for (int i = low; i <= high; i++) {
            {
                if (canAssignPainter(c, a, i)) {
                    result = i;
                    break;
                }
            }
        }
        return result * b % 10000003;
    }

    /**
     * What it does:
     * Optimal solution to the Painter’s Partition problem using binary search on the answer.
     * Returns the minimal time to paint all boards with A painters, modulo 10000003.
     * <p>
     * How it works:
     * - Set the search range for feasible capacities:
     * low = max(c)  → at least the largest board.
     * high = sum(c) → at most all boards by one painter.
     * - Binary search over this range:
     * mid = (low + high) / 2
     * If canAssignPainter(c, a, mid) is true, mid is feasible → try smaller (high = mid - 1).
     * If false, mid is too small → increase lower bound (low = mid + 1).
     * - Loop ends with low pointing to the minimal feasible capacity.
     * - Return (low * b) % 10000003.
     * <p>
     * Intuition:
     * - Feasibility is monotonic: if capacity C works, any C' > C also works.
     * - Binary search finds the smallest feasible capacity efficiently.
     * <p>
     * Example:
     * c = [10, 20, 30, 40], a = 2, b = 1
     * - low = 40, high = 100
     * - mid = 70 → feasible
     * - mid = 55 → not feasible
     * - mid = 60 → feasible
     * - Binary search converges to 60 → answer = 60 * 1 % 10000003 = 60.
     * <p>
     * Time Complexity:
     * - O(n log(sum(c))) because each feasibility check is O(n).
     * - Efficient even for large arrays and large board sizes.
     * <p>
     * Space Complexity:
     * - O(1).
     */
    private static int paintersPartitionOptimal(int[] c, int a, int b) {
        if (c == null || c.length == 0 || a > c.length) return -1;

        int low = Integer.MIN_VALUE;
        int high = 0;
        for (int num : c) {
            low = Math.max(low, num);
            high += num;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canAssignPainter(c, a, mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low * b % 10000003;
    }

    /**
     * What it does:
     * Greedy feasibility check. Determines whether all boards can be painted by
     * ≤ a painters such that no painter paints more than i units.
     * <p>
     * How it works:
     * - Start with the first painter and a running sum of painted units.
     * - For each board:
     * Add its length to currentSum.
     * If currentSum exceeds i, assign a new painter:
     * painterAssigned++, reset currentSum = current board length.
     * If painters used > a, return false.
     * - If we finish with ≤ a painters, return true.
     * <p>
     * Intuition:
     * - Always pack as many boards as possible into the current painter before
     * exceeding i. This greedy approach guarantees the minimum painters for
     * a given capacity.
     * - If this minimal assignment exceeds a, no arrangement can succeed.
     * <p>
     * Example:
     * c = [10, 20, 30, 40], a = 2, i = 60
     * - Painter 1: 10+20+30 = 60
     * - Painter 2: 40
     * - Uses 2 painters ≤ a → return true.
     * <p>
     * Time Complexity:
     * - O(n) to scan through the boards once.
     * <p>
     * Space Complexity:
     * - O(1).
     */
    private static boolean canAssignPainter(int[] c, int a, int i) {
        int painterAssigned = 1;
        int currentSum = 0;
        for (int num : c) {
            currentSum += num;
            if (currentSum > i) {
                painterAssigned++;
                currentSum = num;
                if (painterAssigned > a) {
                    return false;
                }
            }
        }
        return true;
    }
}
