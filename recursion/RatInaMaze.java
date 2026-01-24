package recursion;

import java.util.ArrayList;

/*
Rat in a Maze

Given a grid of dimensions n x n. A rat is placed at coordinates (0, 0) and wants to reach at coordinates (n-1, n-1).
Find all possible paths that rat can take to travel from (0, 0) to (n-1, n-1). The directions in which rat can
move are 'U' (up) , 'D' (down) , 'L' (left) , 'R' (right). The value 0 in grid denotes that the cell is blocked and rat
cannot use that cell for travelling, whereas value 1 represents that rat can travel through the cell.
If the cell (0, 0) has 0 value, then mouse cannot move to any other cell.

Note :
In a path no cell can be visited more than once.
If there is no possible path then return empty vector.

Example 1
Input : n = 4 , grid = [ [1, 0, 0, 0] , [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1] ]
Output : [ "DDRDRR" , "DRDDRR" ]
Explanation : The rat has two different path to reach (3, 3).
The first path is (0, 0) => (1, 0) => (2, 0) => (2, 1) => (3, 1) => (3, 2) => (3, 3).
The second path is (0,0) => (1,0) => (1,1) => (2,1) => (3,1) => (3,2) => (3,3).

Example 2
Input : n = 2 , grid = [ [1, 0] , [1, 0] ]
Output : -1
Explanation : There is no path that rat can choose to travel from (0,0) to (1,1).

Constraints:
            2 <= n <= 5
            0 <= grid[i][j] <= 1
 */
public class RatInaMaze {

    public static void main(String[] args) {

        int[][] grid = {{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}};
        int n = grid.length;
        ArrayList<String> result = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];
        ratInaAMazeBruteForce(0, 0, grid, n, "", result, visited);
        System.out.println(result);
    }

    /**
     * ratInaAMazeBruteForce (Backtracking on a Grid)
     * <p>
     * What it does:
     * Finds all possible paths for a rat to travel from the top-left corner (0, 0)
     * to the bottom-right corner (n-1, n-1) in an NxN grid.
     * The rat can move in four directions: Up ('U'), Down ('D'), Left ('L'), and Right ('R').
     * <p>
     * Core Intuition:
     * We explore every possible step the rat can take.
     * Some cells are blocked (0), and some are open (1).
     * To avoid going in circles, we keep track of where we have already been using a visited array.
     * We use Depth First Search (DFS) to dive deep into a path until we either reach the
     * destination or hit a dead end.
     * <p>
     * Why this is a backtracking problem:
     * At each cell, the rat makes a choice (L, D, U, or R).
     * If a path leads to a dead end or is already visited, we "backtrack" (undo the visit)
     * to try a different direction from the previous cell.
     * This ensures we find every single unique path to the destination.
     * <p>
     * Step-by-step explanation:
     * r, c: The current row and column coordinates of the rat.
     * path: A string that builds up the directions taken so far (e.g., "DDRR").
     * visited: A 2D boolean array to ensure the rat doesn't step on the same cell twice in one path.
     * <p>
     * Base Case 1 (Invalid Move):
     * If the rat goes out of bounds (r < 0, etc.) or hits a blocked cell (grid[r][c] == 0),
     * the function simply returns.
     * <p>
     * Base Case 2 (Success):
     * If r == n - 1 and c == n - 1, the rat has reached the destination!
     * We add the current path string to our result list.
     * <p>
     * Recursive Step:
     * Mark the current cell as visited: visited[r][c] = true.
     * Try moving in all 4 directions in a specific order (often alphabetical or clockwise):
     * Left: (r, c - 1)
     * Down: (r + 1, c)
     * Up: (r - 1, c)
     * Right: (r, c + 1)
     * Backtrack: After exploring all directions from this cell, set visited[r][c] = false.
     * This is crucial because this cell might be part of a different valid path.
     * <p>
     * Example Walkthrough (N=4):
     * Start at (0,0). Move Down to (1,0).
     * From (1,0), move Down to (2,0).
     * From (2,0), move Right to (2,1).
     * Keep moving until (3,3) is reached. Path "DDR..." is stored.
     * The algorithm then backtracks to find if moving Right from (1,0) instead of Down
     * would have led to another path.
     * <p>
     * Complexity:
     * Time: O(4^(N^2)) - In the worst case, each cell has 4 directions to explore.
     * Space: O(N^2) - To store the visited array and the recursion stack.
     */
    private static void ratInaAMazeBruteForce(int r, int c, int[][] grid, int n, String path, ArrayList<String> res, boolean[][] visited) {


        if (r < 0 || r >= n || c < 0 || c >= n || grid[r][c] == 0) {
            return;
        }

        if (r == n - 1 && c == n - 1) {
            res.add(path);
            return;
        }

        if (grid[r][c] == 1 && !visited[r][c]) {
            visited[r][c] = true;
            ratInaAMazeBruteForce(r, c - 1, grid, n, path + "L", res, visited);
            ratInaAMazeBruteForce(r + 1, c, grid, n, path + "D", res, visited);
            ratInaAMazeBruteForce(r - 1, c, grid, n, path + "U", res, visited);
            ratInaAMazeBruteForce(r, c + 1, grid, n, path + "R", res, visited);
            visited[r][c] = false;
        }
    }
}
