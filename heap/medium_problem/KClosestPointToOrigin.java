package heap.medium_problem;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Leetcode 973. K Closest Points to Origin
 * <p>
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k,
 * return the k closest points to the origin (0, 0).
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 * <p>
 * Example 1:
 * <p>
 * Input: points = [[1,3],[-2,2]], k = 1
 * <p>
 * Output: [[-2,2]]
 * <p>
 * Explanation:
 * <p>
 * The distance between (1, 3) and the origin is sqrt(10).
 * <p>
 * The distance between (-2, 2) and the origin is sqrt(8).
 * <p>
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * <p>
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 * <p>
 * Example 2:
 * <p>
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * <p>
 * Output: [[3,3],[-2,4]]
 * <p>
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 * <p>
 * Constraints:
 * <p>
 * 1 <= k <= points.length <= 10^4
 * <p>
 * -10^4 <= xi, yi <= 10^4
 */
public class KClosestPointToOrigin {

    public static void main(String[] args) {
        int[][] points = {{1, 3}, {-2, 2}};
        int k = 1;
        System.out.println(Arrays.deepToString(kClosest(points, k)));
    }


    /**
     * <h2>PROBLEM: K Closest Points to Origin</h2>
     *
     * <h3>REAL-LIFE ANALOGY: The "Overpacked Suitcase"</h3>
     * <hr>
     * <ul>
     * <li>Imagine a suitcase that can only hold <b>K</b> items. You want to bring the <b>K lightest</b> items.</li>
     * <li>You decide the item at the <b>very top</b> must always be the <b>heaviest</b> one currently inside (Max-Heap).</li>
     * <li>Whenever you add a new item and the suitcase is full (size > K), you look at the top and
     * <b>throw away the heaviest item</b> (poll).</li>
     * <li>After checking everything in your closet, the K items left in the suitcase are guaranteed
     * to be the K lightest ones.</li>
     * </ul>
     *
     * <h3>ALGORITHM LOGIC:</h3>
     * <hr>
     * <ol>
     * <li><b>Max-Heap Selection:</b> We use a Max-Heap to track the "closest" points. By keeping the
     * <i>furthest</i> of the current set at the top, we can easily discard it to make room for
     * potentially closer points.</li>
     * <li><b>Distance Optimization:</b> We compare {@code x² + y²} instead of {@code sqrt(x² + y²)}.
     * Since the square root function is monotonic, if {@code A > B}, then {@code sqrt(A) > sqrt(B)}.
     * Skipping {@code Math.sqrt()} saves significant computation.</li>
     * <li><b>Comparator Trick:</b> In Java, {@code (a - b)} is a Min-Heap. We use {@code (distB - distA)}
     * to flip it into a Max-Heap.</li>
     * </ol>
     *
     * <h3>COMPLEXITY:</h3>
     * <hr>
     * <ul>
     * <li><b>Time:</b> {@code O(N log K)} &rarr; We visit N points; each heap operation takes log K.</li>
     * <li><b>Space:</b> {@code O(K)} &rarr; The heap never grows larger than K + 1.</li>
     * </ul>
     */
    private static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) ->
                (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[][] result = new int[k][2];
        while (k > 0) {
            result[--k] = maxHeap.poll();
        }
        return result;
    }
}
