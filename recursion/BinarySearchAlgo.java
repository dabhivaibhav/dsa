package recursion;

public class BinarySearchAlgo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int target1 = 11;

        binarySearchRecursion(arr, target, 0, arr.length - 1);
        binarySearchRecursion(arr, target1, 0, arr.length - 1);
    }


    /**
     * What it does:
     * This is the recursive version of binary search. It calls a helper method
     * that checks the middle element and recurses into either the left or right half.
     * <p>
     * Why it works:
     * The same binary search logic applies — sorted order lets me discard half the elements each call.
     * Recursion breaks the problem into smaller halves until the target is found or the range is empty.
     * <p>
     * Important details:
     * - Base condition: if low > high, the element is not present.
     * - Returns -1 if not found, or the index if found.
     * - Prints the result after the recursive call finishes.
     * - Requires the array to be sorted.
     * <p>
     * Example (nums = [2, 4, 6, 8], target = 4):
     * low=0 high=3 mid=1 → nums[1]=4 == 4 → return 1 → print "found at index 1"
     * <p>
     * Complexity:
     * Time:  O(log n)    (halves the range each call)
     * Space: O(log n)    (recursive call stack depth)
     */
    private static void binarySearchRecursion(int[] arr, int target, int low, int high) {

        if (low > high) {
            System.out.println("No elements found");
            return;
        }
        int mid = (low + high) / 2;
        if (arr[mid] == target) {
            System.out.println("Element found at index: " + mid);
        } else if (arr[mid] < target) {
            binarySearchRecursion(arr, target, mid + 1, high);
        } else {
            binarySearchRecursion(arr, target, low, mid - 1);
        }

    }
}
