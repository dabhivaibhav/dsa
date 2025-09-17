# How to Detect If a Problem Can Be Solved Using Binary Search

This guide helps you develop the intuition to spot problems that can be
solved with **[Binary
Search](https://en.wikipedia.org/wiki/Binary_search_algorithm)**, even
when it's not obvious.

------------------------------------------------------------------------

## Core Clues

### Known Numeric Range

If the possible answer lies between `1` and `n`, even if it's not
directly in the array, you can often binary search over the **answer
space**.

> Example: Find the minimum capacity to ship all packages within D days.

------------------------------------------------------------------------

### Monotonic Property

Binary search needs a **monotone condition**: as `x` increases, `f(x)`
changes only once (from `false` to `true` or vice versa).

> All smaller values fail, all larger values succeed --- or the reverse.

------------------------------------------------------------------------

## Other Subtle Signals

### 1. Feasibility Questions

Look for questions like: \> "Can we do this in X days/capacity/steps?"

If smaller `x` is impossible and larger `x` is always possible, binary
search the threshold.

------------------------------------------------------------------------

### 2. Implicit Sortedness (Hidden Order)

The array may not be sorted, but the **search space is**.

> "What's the smallest radius so all points are covered?"\
> "What's the smallest speed to arrive on time?"

Larger values always work if smaller ones do not --- perfect for binary
search.

------------------------------------------------------------------------

### 3. First True / Last False Pattern

If the solution space looks like:\
`F F F F F T T T T`

Find the boundary where it flips.

> Example: First day stock price ≥ X, first bad version, last valid
> index.

------------------------------------------------------------------------

### 4. Find Peak / Max with Local Comparisons

If you can compare neighbors to find where the slope switches direction,
use binary search on the peak.

> Example: Peak element in a bitonic array (increasing then decreasing).

------------------------------------------------------------------------

### 5. Continuous Domains

You can binary search real numbers if the function is **continuous and
unimodal** (has only one minimum or maximum).

> Example: Minimum time/distance/cost to achieve something with
> precision `1e-6`.

------------------------------------------------------------------------

### 6. Logarithmic Time Requirement

If the problem explicitly says: \> "Solve in O(log n)"

It's a strong hint to consider binary search.

------------------------------------------------------------------------

## Key Mental Trick

Ask yourself:

> "Can I order all possible answers so that once the condition becomes
> true it stays true (or false stays false)?"

If yes → Binary search is probably the right approach.

If no → The problem likely needs another technique (like sliding window,
prefix sums, or two pointers).

------------------------------------------------------------------------

## Bonus: When **Not** to Use Binary Search

-   If the condition is **not monotonic** (zigzags, multiple peaks)\
-   If the property can **flip back and forth** as `x` increases\
-   If the domain is **unordered and cannot be arranged**

------------------------------------------------------------------------
