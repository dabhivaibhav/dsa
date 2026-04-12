package greedy.medium_problems;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Minimum number of platforms required for a railway

Given the arrival and departure times of all trains reaching a particular railway station, determine the minimum number
of platforms required so that no train is kept waiting. Consider all trains arrive and depart on the same day.

In any particular instance, the same platform cannot be used for both the departure of one train and the arrival of
another train, necessitating the use of different platforms in such cases.

Note: Time intervals are in the 24-hour format (HHMM) , where the first two characters represent hour (between 00 to 23)
and the last two characters represent minutes (this will be <= 59 and >= 0). Leading zeros for hours less than 10 are
optional (e.g., 0900 is the same as 900).


Example 1
Input : Arrival = [900, 940, 950, 1100, 1500, 1800] , Departure = [910, 1200, 1120, 1130, 1900, 2000]
Output : 3
Explanation : The first , second , fifth number train can use the platform 1.
The third and sixth train can use the platform 2.
The fourth train will use platform 3.
So total we need 3 different platforms for the railway station so that no train is kept waiting.

Example 2
Input : Arrival = [900, 1100, 1235] , Departure = [1000, 1200, 1240]
Output : 1
Explanation : All the three trains can use the platform 1.
So we required only 1 platform.

Constraints:
            1 <= N <= 10^5
            0000 <= Arrival[i] <= Departure[i] <= 2359
 */
public class MinimumNumberOfPlatformForRailway {

    public static void main(String[] args) {
        int[] arrivals = {900, 940, 950, 1100, 1500, 1800};
        int[] departures = {910, 1200, 1120, 1130, 1900, 2000};
        System.out.println(findPlatformBruteForce(arrivals, departures));
        System.out.println(findPlatformBetter(arrivals, departures));
    }

    /*
    THE BRUTE FORCE SIMULATOR: PHYSICAL PLATFORM TRACKING

    THE INTUITION:
    To solve this in the most "naive" way, we simulate the actual day at the station. We treat each platform as a separate
    "slot" that can hold a value (the departure time of the train currently parked there). When a new train arrives, we
    scan our existing platforms to see if any are empty. If none are empty, we are forced to build a new one.

    ALGORITHM EXPLANATION:
    Create a way to track the departure times of active platforms (like an ArrayList).
    For the first train, "build" the first platform and store its departure time.
    For every subsequent train (i = 1 to N-1): Iterate through all current platforms.
    CHECK: Is the current train's arrival time strictly AFTER the platform's stored departure time?
    IF YES: This platform is free. Update the platform's stored departure time to the current train's departure time and
    move to the next train.
    IF NO (after checking all platforms): No platform is free. Add a new platform to your list and store the current
    train's departure time there.
    The final size of your platform list is the minimum number required.

    CODE EXPLANATION:
    List<Integer> platforms: Each integer in this list represents a platform, and the value is the time that platform
    will become "free."
    nested for-loop: The outer loop visits every train; the inner loop searches for an available "parking spot."
    boolean occupied: A flag to tell us if we found a spot or if we need to call the construction crew for a new platform.

    COMPLEXITY:
    Time: O(N^2) - In the worst case (where all trains overlap), you check 1 platform, then 2, then 3, all the way up to
    N for every train.
    Space: O(N) - To store the departure times of up to N platforms.

     WHY WE OPTIMIZE:
     The O(N^2) approach is slow because for every train, we have to scan all platforms. If we have 100,000 trains, this
     could take billions of operations. To reach the Optimal solution, we stop caring about "which" train is on "which"
     platform and only look at the "Arrival vs. Departure" events in chronological order.

     INTERVIEW TAKEAWAY:
     While this works, interviewers will ask: "Can you do better than O(N^2)?" This is your cue to mention that by
     Sorting the events, you can find the Peak Concurrency (the most trains in the station at once) which is the true answer.
    */
    private static int findPlatformBruteForce(int[] arrivals, int[] departures) {
        if (arrivals.length == 0) return 0;

        // This list will store the departure time of the train currently on each platform
        List<Integer> platforms = new ArrayList<>();

        // Always park the first train on the first platform
        platforms.add(departures[0]);

        for (int i = 1; i < arrivals.length; i++) {
            boolean foundEmptyPlatform = false;

            // Check all existing platforms to see if any are free
            for (int j = 0; j < platforms.size(); j++) {
                // If arrival is after the departure time of a platform
                if (arrivals[i] > platforms.get(j)) {
                    // This platform is now free! Update it with the new departure
                    platforms.set(j, departures[i]);
                    foundEmptyPlatform = true;
                    break;
                }
            }

            // If we checked all platforms and none were free, we need a new one
            if (!foundEmptyPlatform) {
                platforms.add(departures[i]);
            }
        }

        return platforms.size();
    }


    /*
    THE "CONFLICT COUNTER" PATTERN (O(1) SPACE BRUTE FORCE)

    THE INTUITION:
    Instead of acting like a Station Master who manages platforms (which requires a notebook/memory), we act like a
    "Safety Auditor." We pick one train and count how many other trains are physically present in the station during
    that train's arrival and departure window. If 5 trains are in the station at the same time as Train A, we know we
    need at least 5 platforms.

    HOW THE ALGORITHM WORKS (STEP-BY-STEP):
    Create a variable maxPlatforms to store the highest conflict count we find.
    Pick a "Reference Train" (i).
    Reset a currentConflicts counter to 0.
    Compare the Reference Train (i) against every single other train (j) in the schedule.
    IF train 'j' arrives while train 'i' is still at the platform, it counts as a conflict.
    After checking all trains for the current Reference Train, update maxPlatforms.
    Repeat for every train in the list.

    CODE EXPLANATION:
    No Data Structures: We don't use a List, Map, or Heap. We only use two integer variables (maxPlatforms and
    currentConflicts).
    Nested Loops: We compare every train against every other train, resulting in O(N²) time.
    The Condition: (arrivals[j] >= arrivals[i] && arrivals[j] <= departures[i]) checks if train j starts within the
    window of train i.

    COMPLEXITY:
    Time: O(N²) - Because for every train, we scan the entire list again.
    Space: O(1) - We use zero extra memory regardless of how many trains there are.

    INTERVIEW TAKEAWAY:
    This is a "Pure Logic" brute force. While it saves space, it is very slow. In an interview, if they ask to save space
    on the brute force, this is the way. However, usually, they want you to move to the O(N log N) Two-Pointer solution,
    which is both fast AND space-efficient.
    */
    private static int findPlatformBetter(int[] arrivals, int[] departures) {
        int n = arrivals.length;
        int maxPlatforms = 0;

        for (int i = 0; i < n; i++) {
            int currentConflicts = 0;

            for (int j = 0; j < n; j++) {
                // Check if train 'j' is present at the station
                // during any moment of train 'i's stay
                if (arrivals[j] >= arrivals[i] && arrivals[j] <= departures[i]) {
                    currentConflicts++;
                }
            }
            // The maximum number of simultaneous conflicts is the required platforms
            maxPlatforms = Math.max(maxPlatforms, currentConflicts);
        }
        return maxPlatforms;
    }
}
