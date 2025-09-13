package array.easy_problems;

/*
3541. Find Most Frequent Vowel and Consonant

You are given a string s consisting of lowercase English letters ('a' to 'z').
Your task is to:
Find the vowel (one of 'a', 'e', 'i', 'o', or 'u') with the maximum frequency.
Find the consonant (all other letters excluding vowels) with the maximum frequency.
Return the sum of the two frequencies.
Note: If multiple vowels or consonants have the same maximum frequency, you may choose any one of them. If there are no
vowels or no consonants in the string, consider their frequency as 0. The frequency of a letter x is the number of times
it occurs in the string.

Example 1:
Input: s = "successes"
Output: 6
Explanation:
The vowels are: 'u' (frequency 1), 'e' (frequency 2). The maximum frequency is 2.
The consonants are: 's' (frequency 4), 'c' (frequency 2). The maximum frequency is 4.
The output is 2 + 4 = 6.

Example 2:
Input: s = "aeiaeia"
Output: 3
Explanation:
The vowels are: 'a' (frequency 3), 'e' ( frequency 2), 'i' (frequency 2). The maximum frequency is 3.
There are no consonants in s. Hence, maximum consonant frequency = 0.
The output is 3 + 0 = 3.

Constraints:
        1 <= s.length <= 100
        s consists of lowercase English letters only.
 */
public class CountFrequentvowelsConsonants {


    public static void main(String[] args) {
        String sample1 = "successes";
        String sample2 = "aeiaeia";

        System.out.println("Total frequency of "+sample1+" is: " + countFrequentVowelsConsonants(sample1));
        System.out.println("Total frequency of "+sample2+" is: " + countFrequentVowelsConsonants(sample2));
    }

    private static int countFrequentVowelsConsonants(String s) {

        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }

        int maxVowel = 0, maxCons = 0;
        for (int i = 0; i < 26; i++) {
            int f = freq[i];
            if (isVowel(i)) {
                if (f > maxVowel) maxVowel = f;
            } else {
                if (f > maxCons) maxCons = f;
            }
        }
        return maxVowel + maxCons;
    }

    private static boolean isVowel(int idx) {
        return idx == 0 || idx == 4 || idx == 8 || idx == 14 || idx == 20;
    }
}
