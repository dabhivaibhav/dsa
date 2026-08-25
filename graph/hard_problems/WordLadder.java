package graph.hard_problems;

import java.util.*;

/*
Leetcode 127. Word Ladder

A transformation sequence from word beginWord to word endWord using a dictionary wordList is
a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words
in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

Example 1:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.

Example 2:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.

Constraints:
            1 <= beginWord.length <= 10
            endWord.length == beginWord.length
            1 <= wordList.length <= 5000
            wordList[i].length == beginWord.length
            beginWord, endWord, and wordList[i] consist of lowercase English letters.
            beginWord != endWord
            All the words in wordList are unique.
 */
public class WordLadder {


    /*
     * WHAT THIS METHOD DOES:
     * Finds the shortest transformation sequence from beginWord to endWord, where each
     * step changes exactly one letter and every intermediate word must be in the wordList.
     * Models words as graph nodes where an edge exists between two words that differ by
     * exactly one character. BFS with level counting finds the shortest path. Returns the
     * number of words in the shortest sequence, or 0 if no transformation exists.
     * O(N x L x 26) time, O(N) space, where N = word count and L = word length.
     *
     * THE SENTENCE: this is shortest-path BFS on an implicit graph where nodes are words
     * and edges connect words that differ by one letter. The neighbor-finding is the only
     * new part; the BFS skeleton is identical to rotting oranges.
     *
     * ---
     *
     * THE "IMPLICIT GRAPH BFS" PATTERN (WORD LADDER)
     *
     * THE GEAR CHECK:
     * Gear 1 (what is the graph): IMPLICIT. No grid, no adjacency list, no matrix given.
     *   The graph exists in concept: nodes are words, edges connect one-letter-apart words.
     *   You never build the graph explicitly. You discover neighbors on the fly.
     * Gear 2 (what shape is the question): "shortest transformation sequence" = shortest
     *   path. Unweighted (each transformation costs 1). Shortest path + unweighted = BFS.
     * Gear 3 (what tool): BFS with level counting (size snapshot for step counting).
     * Gear 4 (what is special): neighbor-finding by character replacement instead of
     *   direction offsets or adjacency list lookup. That is the ONLY new mechanic.
     *
     * ---
     *
     * THE KEY INSIGHT: THIS IS A GRAPH PROBLEM IN DISGUISE
     *
     * The problem never says "graph." It says "transformation sequence." But the structure
     * IS a graph: each word is a node, and two words are connected (share an edge) if they
     * differ by exactly one letter. "Find the shortest transformation sequence" is "find
     * the shortest path in this graph." Once you see that, the BFS skeleton applies
     * directly.
     *
     * The graph is IMPLICIT: you never build an adjacency list. Instead, you generate
     * neighbors on the fly by trying all one-letter changes and checking if the result
     * exists in the word set. This is the graph equivalent of checking 4 directions on
     * a grid, except here the "directions" are 26 letter replacements at each position.
     *
     * ---
     *
     * MAPPING TO THE BFS SKELETON I ALREADY OWN:
     *
     *   Grid BFS:                           Word Ladder:
     *   for (int[] d : dirs)                for (int j = 0; j < word.length; j++)
     *                                         for (char c = 'a'; c <= 'z'; c++)
     *   int newRow = row + d[0]               chars[j] = c
     *   int newCol = col + d[1]              String newWord = new String(chars)
     *   if (inBounds && grid[r][c]==1)       if (set.contains(newWord))
     *     grid[r][c] = 2                       set.remove(newWord)
     *     queue.add(new int[]{r,c})            queue.add(newWord)
     *
     * Same pattern: generate a candidate neighbor, check if it is valid, mark it visited,
     * enqueue it. The "generate" step changed from coordinate arithmetic to character
     * replacement. Everything around it is identical.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Character replacement (L x 26) instead of comparing every word pair (N x L):
     *    - Why? For each word polled, comparing against all N words in the set costs
     *      O(N x L) per word. Trying 26 replacements at each of L positions costs
     *      O(L x 26) per word. With L <= 10 and N up to 5000, L x 26 = 260 versus
     *      N x L = 50,000. Over 100x faster. The set.contains() call is O(L) for hashing,
     *      making each candidate check O(L), and there are 26L candidates per word.
     *
     * 2. HashSet for the word list, not a List:
     *    - Why? set.contains() is O(L) amortized (hash the string, look it up).
     *      list.contains() is O(N x L) (scan every word, compare each). The set makes
     *      the per-candidate check O(L) instead of O(N x L), which is the entire
     *      performance difference.
     *
     * 3. set.remove() IS the visited mark:
     *    - Why? Same principle as setting grid cells to 2 or 'S'. Once removed, a word
     *      cannot be found by any future set.contains() call, so it cannot be enqueued
     *      again. No separate boolean[] visited needed. The set serves two purposes:
     *      valid-word lookup AND visited tracking.
     *
     * 4. Size snapshot for level counting (steps):
     *    - Why? The answer is "number of words in the shortest sequence," which is
     *      the level count. Each BFS level = one transformation step. Same pattern as
     *      rotting oranges (levels = minutes). steps starts at 1 because the sequence
     *      includes beginWord itself.
     *
     * 5. Queue holds just Strings, no pairing:
     *    - Why? The step count does not need to travel with each word. The size snapshot
     *      handles level counting globally. This avoids the Queue<String, int> problem
     *      (Queue takes one type parameter, and int is not a valid generic type).
     *
     * 6. Early return if endWord is not in the set:
     *    - Why? If the destination does not exist in the word list, no transformation
     *      can ever reach it. Return 0 immediately instead of running a full BFS that
     *      will find nothing.
     *
     * 7. Check word.equals(endWord) at POLL time, not at enqueue time:
     *    - Why? Either works correctly, but checking at poll time is cleaner: you ask
     *      "did I just arrive at the destination?" at the same logical point where grid
     *      BFS would check "is this the target cell?" Both happen at the top of the
     *      processing step.
     *
     * 8. chars[j] = original AFTER the inner loop (restore before next position):
     *    - Why? Without restoring, position j stays mutated when you move to position
     *      j+1. Then you are changing TWO letters from the original word (j is still
     *      altered, j+1 is being altered), which generates words that differ by two
     *      characters, not one. The restore is what keeps each candidate exactly one
     *      letter different from the original word.
     *
     * ---
     *
     * MISTAKES IN MY FIRST ATTEMPT:
     * - steps started at 0 instead of 1: the sequence INCLUDES beginWord, so the first
     *   word is already step 1. Starting at 0 would undercount by 1.
     * - char original = wordArray[i] was OUTSIDE the for-loop declaring i: variable i
     *   did not exist yet. The save-and-restore must be INSIDE the position loop.
     * - word.length instead of word.length(): length is a field on arrays, length() is
     *   a method on Strings. Missing parentheses = won't compile.
     * - No size snapshot: without it, no level counting, no way to track steps.
     * - No set.contains() check: generated candidate words but never checked if they
     *   were valid words in the list.
     * - No set.remove(): no visited marking, same word could be enqueued by multiple
     *   parents.
     * - No endWord check: even if the destination was reached, nothing returned it.
     * - Tried Queue<String, int>: Queue takes one type parameter. Solved by using the
     *   size snapshot for level counting instead of pairing step count with each word.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Build a HashSet from wordList. If endWord is not in it, return 0.
     * Step 2: Enqueue beginWord. Remove it from set. steps = 1.
     * Step 3: BFS with size snapshot:
     *         - For each word in the current level:
     *           - If it equals endWord, return steps.
     *           - Convert to char[]. For each position j:
     *             - Save original char. Try a-z (skip original).
     *             - If new string is in the set: remove it, enqueue it.
     *             - Restore original char.
     *         - After the level: steps++.
     * Step 4: Queue empty, endWord never reached: return 0.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Let N = number of words in wordList, L = length of each word.
     *
     * TIME:
     *   Building the set:             O(N x L). Hashing each word costs O(L).
     *   BFS: each word is polled at most once (removed from set prevents re-enqueue).
     *     Per word polled:
     *       - Convert to char[]:      O(L).
     *       - For each of L positions, try 26 letters:  L x 26 candidates.
     *       - Each candidate: build String O(L), hash and check set O(L).
     *       - Per word total:         O(L x 26 x L) = O(26 x L^2).
     *     Total across all words:     O(N x 26 x L^2).
     *   TOTAL: O(N x L^2). (The 26 is a constant factor.)
     *   With L <= 10, this is roughly O(N x 100) = O(100N), very fast.
     *
     * SPACE:
     *   Set: O(N x L). Stores N words of length L each.
     *   Queue: O(N) worst case (every word enqueued).
     *   char[] per word: O(L), reused each iteration.
     *   TOTAL: O(N x L).
     *
     * ---
     *
     * THE GENERAL PATTERN (carry this to future problems):
     *
     * Some graph problems give you explicit edges (adjacency list, matrix, grid). Others
     * give you an IMPLICIT graph where nodes and edges are defined by a RULE ("words
     * differing by one letter are connected"). The BFS skeleton is identical; only the
     * neighbor-finding changes. Recognizing "this is a graph problem" when there is no
     * graph in the input is the skill this problem teaches.
     *
     * Other examples of implicit graphs:
     * - Open the Lock: nodes are lock states "0000"-"9999", edges are one-digit turns.
     * - Minimum Genetic Mutation: nodes are gene strings, edges are one-char changes.
     * - Sliding Puzzle: nodes are board states, edges are one-tile slides.
     * All use the same BFS skeleton with problem-specific neighbor generation.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "this is shortest-path BFS on an implicit word graph" first. It frames the
     *   whole solution and shows you see the graph underneath the string problem.
     * - The neighbor-finding strategy (L x 26 vs N x L) is the main design decision.
     *   State both, say why L x 26 wins, and the interviewer knows you considered the
     *   alternative.
     * - set.remove() as visited marking is clean and avoids a separate data structure.
     *   Say it explicitly: "removal from the set IS my visited mechanism."
     * - The char[] save-and-restore is the most common bug. Forgetting to restore means
     *   generating two-letter-apart words. Name it before the interviewer has to ask.
     * - steps starts at 1 because the problem counts words in the sequence, not edges.
     *   "hit" -> "hot" -> "dot" -> "dog" -> "cog" is 5 words, 4 edges. The answer is 5.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);
        if (!set.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        set.remove(beginWord);
        int steps = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String word = queue.poll();

                if (word.equals(endWord)) return steps;

                char[] chars = word.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;

                        chars[j] = c;
                        String newWord = new String(chars);

                        if (set.contains(newWord)) {
                            set.remove(newWord);
                            queue.add(newWord);
                        }
                    }

                    chars[j] = original;
                }
            }
            steps++;
        }

        return 0;
    }
}
