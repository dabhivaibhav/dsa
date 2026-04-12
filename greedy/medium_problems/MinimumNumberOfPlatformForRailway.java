package greedy.medium_problems;

import java.util.ArrayList;
import java.util.Arrays;
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

    THE FOUR SCENARIOS OF TRAIN INTERACTION (INTERVAL OVERLAP LOGIC):
    To calculate the minimum platforms without extra space, we must understand how any two
    trains (Train A and Train B) can exist in the station relative to each other.
    If Train A is our "Reference Train," we check if Train B conflicts with it.

    SCENARIO 1: THE "OVERLAP START" (PARTIAL CONFLICT)
    Logic: Train B arrives BEFORE Train A leaves, but stays LONGER than Train A.
    Check: (Arrival B <= Departure A) AND (Departure B > Departure A)
    Reality: While Train A is occupying a platform, Train B rolls in. They are
    simultaneously present.

    SCENARIO 2: THE "CONTAINED" (FULL CONFLICT)
    Logic: Train B's entire stay happens WHILE Train A is at the station.
    Check: (Arrival B >= Arrival A) AND (Departure B <= Departure A)
    Reality: Train B pulls in after A and leaves before A. It completely
    occupies a second platform during Train A's stay.

    SCENARIO 3: THE "OVERLAP END" (PARTIAL CONFLICT)
    Logic: Train B was ALREADY there when Train A arrived, then Train B leaves.
    Check: (Arrival B < Arrival A) AND (Departure B >= Arrival A)
    Reality: When Train A pulls into the station, it sees Train B already
    sitting on a platform.

    SCENARIO 4: THE "NON-OVERLAP" (NO CONFLICT)
    Logic: Train B leaves before A arrives, OR Train B arrives after A leaves.
    Check: (Departure B < Arrival A) OR (Arrival B > Departure A)
    Reality: These two trains are like "ships passing in the night." They never
    occupy the station at the same time. One platform can serve both.

    THE "UNIVERSAL OVERLAP RULE" FOR CODE:
    Instead of checking all 4 scenarios, we can simplify the math.
    Two trains (A and B) CONFLICT if this single condition is true:

    (Arrival B <= Departure A) AND (Departure B >= Arrival A)
    If this is true, they overlap. In our O(1) space brute force, we pick one
    train 'i' as the reference and count how many trains 'j' satisfy this rule.
    The peak count is the minimum number of platforms required.

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

    /*
    THE "CHRONOLOGICAL EVENT" PATTERN (OPTIMAL)

    What this method does:
    Calculates the minimum platforms by tracking the peak number of trains present at the station simultaneously.

    Core Idea:
    Stop tracking "Platforms" and start tracking "Events." Every arrival is a +1 to demand, and every departure is a -1
    to demand. By sorting arrival and departure times separately, we can simulate the passage of time.

    INTUITION (VERY IMPORTANT):
    Why can we sort them separately?
    In the brute force, we kept Arrival[i] and Departure[i] together. But platforms are interchangeable. If you have 3
    trains in the station, it doesn't matter which one leaves first; any departure frees up a space. By sorting
    independently, we are essentially saying: "Show me the next thing that happens in this station, regardless of which
    train it is."

    How the Code Works:
    Step 1: Sort Arrivals and Departures. This creates two timelines.
    Step 2: Use two pointers (i, j) to compare the next arrival with the next departure.
    Step 3: If the next arrival happens before (or at the same time as) the next departure, we must have another
            platform ready.
    Step 4: If a departure happens first, we "release" a platform to be used by future arrivals.
    Step 5: Record the "Peak" value during this process.

    Complexity:
    → Time Complexity: O(N log N)
    (Due to sorting. The while loop is O(N))
    → Space Complexity: O(1)
    (If we sort the input arrays directly, otherwise O(N) to copy them)

    Interview Takeaway:
    This is the "Sweep Line" algorithm in its simplest form. Whenever you need to find the maximum number of concurrent/
    overlapping intervals, the most efficient way is to break the intervals into "Start" and "End" events and sort them.
    */
    public static int findPlatformOptimal(int[] arr, int[] dep) {
        int n = arr.length;

        // Sort both arrays independently
        Arrays.sort(arr);
        Arrays.sort(dep);

        // Initialize pointers and counters
        int plat_needed = 0;
        int max_platforms = 0;
        int i = 0; // Pointer for arrival
        int j = 0; // Pointer for departure

        // Walk through the timeline
        while (i < n && j < n) {
            // If a train is arriving (or arriving and departing at the same time)
            if (arr[i] <= dep[j]) {
                plat_needed++;
                i++;
            }
            // If a train is departing
            else {
                plat_needed--;
                j++;
            }
            // Update the peak concurrency
            max_platforms = Math.max(max_platforms, plat_needed);
        }
        return max_platforms;
    }
}
