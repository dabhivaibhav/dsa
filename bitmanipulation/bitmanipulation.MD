# 🧠 Bit Manipulation – The Ultimate DSA README

> A **from‑first‑principles**, interview‑ready guide to Bit Manipulation.
> Read this once carefully, revisit it before interviews, and your brain will *rewind* every concept on demand.

---

## 📌 What Is a Bit?

A **bit (binary digit)** is the smallest unit of data in a computer.

* It can have **only two values**: `0` or `1`
* Everything in a computer—numbers, characters, images, videos, programs—is ultimately built from bits

Think of bits as **tiny switches**:

* `0` → OFF
* `1` → ON

A group of bits forms larger units:

* **8 bits** = 1 byte
* **32 bits** = typical `int`
* **64 bits** = typical `long`

---

## ❓ Why Do We Need Bit Manipulation?

Bit manipulation is the art of **directly controlling these switches**.

### 🚀 Why it matters:

### 1️⃣ Performance

Bitwise operations are **single CPU instructions**.

* Faster than `+`, `-`, `*`, `/`
* Extremely cache‑friendly

Example:

```java
x * 2   // slower
x << 1  // faster
```

### 2️⃣ Space Optimization

One `int` (32 bits) can store **32 boolean states**.

* Flags
* Visited nodes
* Permissions
* Subset masks

This turns `O(N)` space into **O(1)**.

### 3️⃣ Low‑Level & Systems Programming

Bit manipulation is unavoidable in:

* OS kernels
* Device drivers
* Cryptography
* Compression
* Networking
* Embedded systems

### 4️⃣ DSA & Interview Gold

Bit tricks often:

* Reduce space complexity
* Enable elegant solutions
* Turn brute force into optimal

If interviews had cheat codes, **bits are the Konami code**.

---

## 💻 How Does a Computer Store Numbers?

Computers use **binary (base‑2)**, not decimal.

Each position represents a power of 2:

```
128 64 32 16 8 4 2 1
```

---

## ➕ How Positive Numbers Are Stored

Positive numbers are stored as **normal binary**.

Example (8‑bit system):

```
5 = 00000101
```

No tricks. No magic. Just binary.

---

## ➖ How Negative Numbers Are Stored (2’s Complement)

Computers **do not store a minus sign**.

Instead:

* The **Most Significant Bit (MSB)** acts as the **sign bit**

    * `0` → positive
    * `1` → negative

Negative numbers are stored using **2’s Complement**.

### Why 2’s Complement?

* One representation for zero
* Same hardware for addition & subtraction
* Overflow detection becomes simpler

---

## 🔄 Binary ↔ Decimal Conversion

### Binary → Decimal

Multiply each bit by `2^position`.

Example:

```
1011 = (1×2³) + (0×2²) + (1×2¹) + (1×2⁰)
     = 8 + 0 + 2 + 1
     = 11
```

### Decimal → Binary

Repeatedly divide by 2 and collect remainders (bottom → top).

Example:

```
13 / 2 = 6  rem 1
6  / 2 = 3  rem 0
3  / 2 = 1  rem 1
1  / 2 = 0  rem 1

Binary = 1101
```

---

## 🧮 1’s Complement & 2’s Complement

### 1’s Complement

Flip every bit.

```
0 → 1
1 → 0
```

Example:

```
5  = 00000101
~5 = 11111010
```

### 2’s Complement

```
2’s Complement = 1’s Complement + 1
```

Example: `-5`

```
+5              = 00000101
1’s Complement  = 11111010
+1              = 11111011  → -5
```

This representation is **universally used**.

---

## 🛠️ Bitwise Operators

| Operator | Symbol | Rule                      |                            |
| -------- | ------ | ------------------------- | -------------------------- |
| AND      | `&`    | 1 only if both bits are 1 |                            |
| OR       | `      | `                         | 1 if at least one bit is 1 |
| XOR      | `^`    | 1 if bits differ          |                            |
| NOT      | `~`    | Flips all bits            |                            |

### ✅ Truth Table Snapshot

```
A B | A&B A|B A^B
0 0 |  0   0   0
0 1 |  0   1   1
1 0 |  0   1   1
1 1 |  1   1   0
```

### 🧪 Detailed Examples (Binary → Operation → Decimal)

> Below, we’ll use **8-bit** binary just for readability. Real Java `int` is **32-bit**.

#### 1) AND `&` ("keep only the common 1s")

Example: `a = 13`, `b = 10`

* `13  = 00001101`
* `10  = 00001010`

Bitwise AND:

```
  00001101
& 00001010
----------
  00001000
```

Result: `00001000 = 8`

**How to think:** a bit becomes 1 **only** where both have 1.

---

#### 2) OR `|` ("turn on bits present in either")

Example: `a = 13`, `b = 10`

* `13  = 00001101`
* `10  = 00001010`

Bitwise OR:

```
  00001101
| 00001010
----------
  00001111
```

Result: `00001111 = 15`

**How to think:** if either side has 1, result bit is 1.

---

#### 3) XOR `^` ("different bits only")

Example: `a = 13`, `b = 10`

* `13  = 00001101`
* `10  = 00001010`

Bitwise XOR:

```
  00001101
^ 00001010
----------
  00000111
```

Result: `00000111 = 7`

**How to think:** 1 appears where bits are different (like a mismatch detector).

---

#### 4) NOT `~` ("flip every bit")

Example: `a = 13`

* `13 = 00001101`

Bitwise NOT:

```
~ 00001101
----------
  11110010
```

As **unsigned 8-bit**, `11110010` = 242.

But in Java, `int` is signed 32-bit, so `~13` becomes **negative**.

**Key identity (interview-safe):**

```
~x = -(x + 1)
```

So:

```
~13 = -(13 + 1) = -14
```

---

### 1️⃣ AND (`&`)

**Rule:** Result bit is `1` **only if both bits are 1**.

Example:

```text
A = 5  → 00000101
B = 3  → 00000011

A & B → 00000001
```

Step-by-step:

```
  00000101   (5)
& 00000011   (3)
-----------
  00000001   (1)
```

✅ Final Answer: `1`

---

### 2️⃣ OR (`|`)

**Rule:** Result bit is `1` if **at least one bit is 1**.

Example:

```text
A = 5 → 00000101
B = 3 → 00000011

A | B → 00000111
```

Step-by-step:

```
  00000101   (5)
| 00000011   (3)
-----------
  00000111   (7)
```

✅ Final Answer: `7`

---

### 3️⃣ XOR (`^`)

**Rule:** Result bit is `1` **only if bits are different**.

Example:

```text
A = 5 → 00000101
B = 3 → 00000011

A ^ B → 00000110
```

Step-by-step:

```
  00000101   (5)
^ 00000011   (3)
-----------
  00000110   (6)
```

✅ Final Answer: `6`

---

### 4️⃣ NOT (`~`)

**Rule:** Flip every bit (1’s Complement).

Example:

```text
A = 5 → 00000101
~A    → 11111010
```

Step-by-step:

```
~00000101 = 11111010
```

This result is **negative** (MSB = 1).

Decimal value:

```
11111010 → -6
```

Why?

* 1’s complement → `00000101`
* +1 → `00000110` → 6
* Negative sign → `-6`

✅ Final Answer: `-6`

---

## 🏹 Shift Operators

Shifts move bits left or right by **k positions**.

### The golden rule (for positive numbers)

* `n << k`  ≈  `n * 2^k`
* `n >> k`  ≈  `n / 2^k`

> In shifts, **k = how many times you move**.
>
> * If `k = 1`, you move bits **1 position**.
> * If `k = 2`, you move bits **2 positions**.

| Operator | Name                     | What it does to bits                                     |
| -------- | ------------------------ | -------------------------------------------------------- |
| `<<`     | Left Shift               | shift left, fill 0s on the right                         |
| `>>`     | Right Shift (Arithmetic) | shift right, copy the sign bit (keeps negative negative) |
| `>>>`    | Unsigned Right Shift     | shift right, fill 0s on the left                         |

---

### 🧪 Detailed Shift Examples (showing movement)

#### 1) Left Shift `<<` (shift left, add 0s on the right)

Example: `n = 5`

* Decimal: `5`
* Binary (8-bit): `00000101`

**Shift left by 1** (`5 << 1`):

* Instruction: move every bit **1 step left**, put `0` in the rightmost place.

```
Before: 00000101
After : 00001010
```

Result: `00001010 = 10`

**Shift left by 2** (`5 << 2`):

```
Before: 00000101
After : 00010100
```

Result: `00010100 = 20`

✅ Intuition: each left shift doubles (for non-overflowing positives).

---

#### 2) Right Shift `>>` (arithmetic shift, keeps the sign)

Example: `n = 20`

* Decimal: `20`
* Binary: `00010100`

**Shift right by 1** (`20 >> 1`):

* Instruction: move every bit **1 step right**, drop the rightmost bit.
* Leftmost bit is filled with the **sign bit** (0 for positive).

```
Before: 00010100
After : 00001010
```

Result: `00001010 = 10`

**Shift right by 2** (`20 >> 2`):

```
Before: 00010100
After : 00000101
```

Result: `00000101 = 5`

✅ Intuition: each arithmetic right shift halves (for positives).

---

#### 3) Right Shift `>>` on a Negative Number (sign extension)

Example: `n = -5` (8-bit view)

First, represent `-5` in 2’s complement:

* `+5 = 00000101`
* 1’s complement: `11111010`
* +1 → `11111011`  (this is `-5`)

So:

* `-5 = 11111011`

**Shift right by 1** (`-5 >> 1`):

* Instruction: shift right, and since number is negative, fill left with **1**.

```
Before: 11111011
After : 11111101
```

`11111101` (two’s complement) equals `-3`.

So:

* `-5 >> 1 = -3`

That “rounding toward -∞” behavior is a classic gotcha.

---

#### 4) Unsigned Right Shift `>>>` (zero-fill)

This is Java-specific and **super important** for dealing with negative numbers.

Example: `n = -5` (8-bit view again)

* `-5 = 11111011`

**Unsigned shift right by 1** (`-5 >>> 1`):

* Instruction: shift right and fill left with **0** no matter what.

```
Before: 11111011
After : 01111101
```

As unsigned 8-bit, `01111101 = 125`.

In real Java (32-bit int), `-5 >>> 1` becomes a **large positive** number.

✅ When to use `>>>`:

* when you want to treat the bits as an **unsigned** value
* when you’re doing bit-level algorithms and don’t want sign-extension

---

### 5️⃣ Left Shift (`<<`)

**Rule:**

* Shift bits **left** by `k`
* Fill right side with `0`
* Equivalent to: `number × 2^k`

#### Example 1: `1 << 1`

Start:

```
1 → 00000001
```

Move bits **1 position to the left**:

```
00000001 << 1 = 00000010
```

Decimal:

```
00000010 = 2
```

✅ Result: `1 << 1 = 2`

---

#### Example 2: `5 << 2`

Start:

```
5 → 00000101
```

Shift left by **2 positions**:

```
00000101 << 2 = 00010100
```

Decimal:

```
00010100 = 20
```

Why?

```
5 × 2² = 5 × 4 = 20
```

✅ Result: `20`

---

### 6️⃣ Right Shift (`>>`) — Signed

**Rule:**

* Shift bits **right** by `k`
* **Preserve sign bit** (MSB)
* Equivalent to: `number ÷ 2^k`

#### Example: `8 >> 1`

Start:

```
8 → 00001000
```

Shift right by 1:

```
00001000 >> 1 = 00000100
```

Decimal:

```
00000100 = 4
```

✅ Result: `8 >> 1 = 4`

---

#### Negative Number Example: `-8 >> 1`

Start (2’s complement):

```
-8 → 11111000
```

Shift right:

```
11111000 >> 1 = 11111100
```

Decimal:

```
11111100 = -4
```

Sign bit (`1`) is preserved.

---

### 7️⃣ Unsigned Right Shift (`>>>`)

**Rule:**

* Shift right
* Always fill MSB with `0`
* Ignores sign

#### Example: `-8 >>> 1`

Start:

```
-8 → 11111000
```

Unsigned shift:

```
11111000 >>> 1 = 01111100
```

Decimal:

```
01111100 = 124
```

This is why `>>>` is dangerous but powerful.

--------|--------|------|
| AND | `&` | 1 only if both bits are 1 |
| OR | `|` | 1 if at least one bit is 1 |
| XOR | `^` | 1 if bits differ |
| NOT | `~` | Flips all bits |

### Truth Table Snapshot

```
A B | A&B A|B A^B
0 0 |  0   0   0
0 1 |  0   1   1
1 0 |  0   1   1
1 1 |  1   1   0
```

---

## ⚡ XOR – The Interview Wizard

XOR has magical properties:

```
A ^ A = 0
A ^ 0 = A
A ^ B = B ^ A
(A ^ B) ^ B = A
```

Used for:

* Finding unique elements
* Swapping numbers
* Cancelling duplicates

---

## 🏹 Shift Operators

Shifts move bits left or right.

| Operator | Name                 | Effect                           |
| -------- | -------------------- | -------------------------------- |
| `<<`     | Left Shift           | Multiply by `2^k`                |
| `>>`     | Right Shift          | Divide by `2^k` (sign preserved) |
| `>>>`    | Unsigned Right Shift | Zero‑fill                        |

Example:

```
5 << 1 = 10
5 >> 1 = 2
```

---

## 🔧 Core Bit Manipulation Patterns

### 1️⃣ Check Even / Odd

```java
(n & 1) == 0  // Even
```

### 2️⃣ Check Power of 2

```java
n > 0 && (n & (n - 1)) == 0
```

### 3️⃣ Get i-th Bit

```java
(n >> i) & 1
```

### 4️⃣ Set i-th Bit

```java
n | (1 << i)
```

### 5️⃣ Clear i-th Bit

```java
n & ~(1 << i)
```

### 6️⃣ Toggle i-th Bit

```java
n ^ (1 << i)
```

### 7️⃣ Remove Lowest Set Bit

```java
n & (n - 1)
```

Basis of **Brian Kernighan’s Algorithm**.

---

## 🧠 Bits vs Arithmetic (Interview Table)

| Operation | Arithmetic    | Bitwise  |
| --------- | ------------- | -------- |
| ×2        | `n * 2`       | `n << 1` |
| ÷2        | `n / 2`       | `n >> 1` |
| Odd check | `n % 2`       | `n & 1`  |
| Swap      | Temp variable | XOR swap |

---

## 🏁 Mental Model for DSA Rounds

When you see:

* **Subsets** → Think **bitmask**
* **Unique element** → Think **XOR**
* **Constraints ≤ 32 or 64** → Think **bits**
* **Space optimization** → Think **flags**

Bits reward those who **see structure instead of numbers**.

---

## 🎯 Final Takeaway

Bit Manipulation is not about memorizing tricks.

It’s about:

* Understanding **how computers think**
* Exploiting **binary structure**
* Writing **elegant, optimal solutions**

Master bits once — they will pay rent in **every DSA round**.

---

🧠 *Read it slow. Revisit it often. Let the bits do the heavy lifting.*
