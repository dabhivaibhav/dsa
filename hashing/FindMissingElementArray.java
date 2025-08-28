package hashing;

/*
Given an integer array of size n containing distinct values in the range from 0 to n (inclusive),
return the only number missing from the array within this range.
Constraints:
            n == nums.length
            1 <= n <= 104
            0 <= nums[i] <= n
            All the numbers of nums are unique.
 */
public class FindMissingElementArray {

    public static void main(String[] args) {

        int[] num1 = {0, 1, 2, 3, 4, 5, 6, 8};
        int n = num1.length;
        System.out.println("brute-force approach: " + (bruteForceApproach(num1, n)));
        System.out.println("better approach (Hashing)" + (betterHashingApproach(num1, n)));
        System.out.println("Optimal approach 1 (Using sum): " + (optimalApproach1(num1, n)));
        System.out.println("Optimal approach 1 (Using sum): " + (optimalApproach2(num1, n)));
    }

    /*
    I have used the brute force approach to solve this problem. I have used while loop which will iterate through
    the given "n". and another for loop to iterate over the array and check if the current element
    is missing in the array. If yes, then return that element.
    Time Complexity: O(n^2)
    Space Complexity: O(1)
     */
    private static int bruteForceApproach(int[] num1, int n) {
        int missing = 0;
        while (missing < n) {
            int flag = 0;
            for (int i = 0; i < n; i++) {
                if (num1[i] == missing) {
                    missing++;
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return missing;
            }
        }
        return missing;
    }


    /*
    I have used Hashing approach to solve this problem. Here I am using an array of size n+1 to store the count of each element.
    I iterate over the array and increment the count of each element by 1. After that I iterate over the array and check
    if any element has count as 0. If yes, then return that element.
    Time Complexity: O(n) + O(n)
    Space Complexity: O(n)
     */
    private static int betterHashingApproach(int[] num1, int n) {
        int[] arr = new int[n + 1];

        for (int i = 0; i < n; i++) {
            arr[num1[i]] += 1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) return i;
        }

        return 0;
    }


    /*
    In this optimal approach, I have used the formula of sum of first n natural numbers to find the missing element.
    Time Complexity: O(n)
    Space Complexity: O(1)
     */
    private static int optimalApproach1(int[] num1, int n) {

        int sum = 0;
        int sumOfN = n * (n + 1) / 2;
        for (int i = 0; i < n; i++) {
            sum += num1[i];
        }

        return sumOfN - sum;
    }

    /*
    Optimal XOR Approach:
    1. Uses XOR properties: a^a = 0 and a^0 = a
    2. XORs all array elements together (xor1)
    3. XORs all numbers from 0 to n (xor2)
    4. The XOR of xor1 and xor2 gives missing number
    5. Handles integer overflow better than sum approach
    Time Complexity: O(n) for two separate iterations
    Space Complexity: O(1) as we only use XOR variables
     */
    private static int optimalApproach2(int[] num1, int n) {
        int xor1 = 0;
        for (int i = 0; i < n; i++) {
            xor1 ^= num1[i];
        }
        int xor2 = n;
        for (int i = 0; i < n; i++) {
            xor2 ^= i;
        }
        return xor1 ^ xor2;
    }

}
