package greedy.medium_problems;

import java.util.Arrays;

/*
Leetcode 322. Coin Change

You are given an integer array coins representing coins of different denominations and an integer amount representing a
total amount of money. Return the fewest number of coins that you need to make up that amount. If that amount of money
cannot be made up by any combination of the coins, return -1. You may assume that you have an infinite number of each kind of coin.

Example 1:
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1

Example 2:
Input: coins = [2], amount = 3
Output: -1

Example 3:
Input: coins = [1], amount = 0
Output: 0

Constraints:
            1 <= coins.length <= 12
            1 <= coins[i] <= 2^31 - 1
            0 <= amount <= 10^4

 */
public class CoinChange {

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println(coinChange(coins, amount));

    }

    private static int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;

        Arrays.sort(coins);
        int index = coins.length;
        int n = coins.length;
        int totalCoins = 0;
        while (amount > 0 && index > 0) {
            if (coins[index - 1] <= amount) {
                amount -= coins[index - 1];
                totalCoins++;
            } else {
                index--;
            }

        }
        return amount > 0 ? -1 : totalCoins;
    }
}
