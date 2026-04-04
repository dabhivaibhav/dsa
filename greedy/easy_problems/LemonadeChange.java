package greedy.easy_problems;

/*
Leetcode 860. Lemonade Change

At a lemonade stand, each lemonade costs $5. Customers are standing in a queue to buy from you and order one at a time
(in the order specified by bills). Each customer will only buy one lemonade and pay with either a $5, $10, or $20 bill.
You must provide the correct change to each customer so that the net transaction is that the customer pays $5.
Note that you do not have any change in hand at first.
Given an integer array bills where bills[i] is the bill the ith customer pays, return true if you can provide every
customer with the correct change, or false otherwise.

Example 1:
Input: bills = [5,5,5,10,20]
Output: true
Explanation:
From the first 3 customers, we collect three $5 bills in order.
From the fourth customer, we collect a $10 bill and give back a $5.
From the fifth customer, we give a $10 bill and a $5 bill.
Since all customers got correct change, we output true.

Example 2:
Input: bills = [5,5,10,10,20]
Output: false
Explanation:
From the first two customers in order, we collect two $5 bills.
For the next two customers in order, we collect a $10 bill and give back a $5 bill.
For the last customer, we can not give the change of $15 back because we only have two $10 bills.
Since not every customer received the correct change, the answer is false.

Constraints:
            1 <= bills.length <= 10^5
            bills[i] is either 5, 10, or 20.
 */
public class LemonadeChange {

    public static void main(String args[]) {
        int[] bills = {5, 5, 5, 10, 20};
        System.out.println(lemonadeChange(bills));
    }

    /**
     * lemonadeChange(int[] bills)
     * <p>
     * What this method does:
     * <p>
     * Determines whether we can provide
     * correct change to every customer
     * in the given order.
     * <p>
     * <p>
     * Problem Setup:
     * <p>
     * Each lemonade costs $5.
     * <p>
     * Customers pay using:
     * <p>
     * → $5, $10, or $20 bills
     * <p>
     * We must return correct change
     * using the bills we currently have.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * Simulate each transaction
     * and maintain count of bills:
     * <p>
     * → five  → number of $5 bills
     * → ten   → number of $10 bills
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * We process customers one by one.
     * <p>
     * At each step:
     * <p>
     * → Try to give exact change
     * → If not possible → return false
     * <p>
     * <p>
     * Key Greedy Insight:
     * <p>
     * Always prioritize using:
     * <p>
     * → $10 + $5 (for $20 change)
     * instead of
     * → three $5 bills
     * <p>
     * <p>
     * Why?
     * <p>
     * Because:
     * <p>
     * $5 bills are more flexible
     * and needed for future transactions.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Initialize:
     * <p>
     * five = 0
     * ten  = 0
     * <p>
     * <p>
     * Step 2:
     * <p>
     * Traverse each bill:
     * <p>
     * <p>
     * Case 1: bill == 5
     * <p>
     * → No change needed
     * → Just collect it
     * <p>
     * five++
     * <p>
     * <p>
     * Case 2: bill == 10
     * <p>
     * → Need to give $5 change
     * <p>
     * If five > 0:
     * <p>
     * → give one $5
     * → five--
     * → ten++
     * <p>
     * Else:
     * <p>
     * → cannot give change
     * → return false
     * <p>
     * <p>
     * Case 3: bill == 20
     * <p>
     * → Need to give $15 change
     * <p>
     * Priority 1:
     * <p>
     * If (ten > 0 AND five > 0):
     * <p>
     * → give $10 + $5
     * → ten--
     * → five--
     * <p>
     * <p>
     * Priority 2:
     * <p>
     * Else if (five ≥ 3):
     * <p>
     * → give three $5 bills
     * → five -= 3
     * <p>
     * <p>
     * Else:
     * <p>
     * → cannot give change
     * → return false
     * <p>
     * <p>
     * Step 3:
     * <p>
     * If all customers processed:
     * <p>
     * → return true
     * <p>
     * <p>
     * Example:
     * <p>
     * bills = [5, 5, 5, 10, 20]
     * <p>
     * Step-by-step:
     * <p>
     * 5  → five = 1
     * 5  → five = 2
     * 5  → five = 3
     * 10 → give 5 → five = 2, ten = 1
     * 20 → give 10 + 5 → five = 1, ten = 0
     * <p>
     * Result = true
     * <p>
     * <p>
     * Failure Example:
     * <p>
     * bills = [5, 10, 20]
     * <p>
     * 5  → five = 1
     * 10 → give 5 → five = 0, ten = 1
     * 20 → need 15 → cannot (no $5)
     * <p>
     * Result = false
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(n)
     * <p>
     * → Single pass through bills
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * → Only counters used
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a GREEDY problem.
     * <p>
     * <p>
     * Key Pattern:
     * <p>
     * → Make the best local decision
     * (use larger bills first for change)
     * <p>
     * <p>
     * Golden Rule:
     * <p>
     * Preserve smaller denominations ($5)
     * because they are more useful later.
     */
    private static boolean lemonadeChange(int[] bills) {

        int five = 0;
        int ten = 0;

        for (int bill : bills) {
            if (bill == 5) {
                five++;
            }
            if (bill == 10) {
                if (five > 0) {
                    ten++;
                    five--;
                } else {
                    return false;
                }

            }
            if (bill == 20) {
                if (ten > 0 && five > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
