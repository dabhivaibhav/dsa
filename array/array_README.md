
# Java Arrays: Complete and Elaborated Guide

Arrays in Java are fundamental data structures that allow you to store multiple values of the same type in a single variable.

---

## 🔰 What is an Array?

An **array** is a collection of elements of the same type stored in a **contiguous memory location**. It allows you to group data and access it using indices.

---

## 🔑 Key Characteristics

1. **Fixed-size** – Once an array is declared, its size cannot be changed.
2. **Index-based access** – Elements are accessed using zero-based indices.
3. **Contiguous memory** – Elements are stored in a continuous block of memory, which improves performance.
4. **Homogeneous elements** – All elements in the array must be of the same data type.

---

## ✅ Benefits of Using Arrays

1. **Fast access** – Accessing any element using its index has O(1) time complexity.
2. **Cache-friendly** – Due to contiguous memory layout, arrays are efficient for CPU cache.
3. **Simpler memory usage** – No overhead of dynamic resizing as in dynamic data structures.

---

## 🔄 Array Passing Behavior in Java

- In Java, arrays are **passed by reference**.
- This means if a method modifies the array, the changes will be reflected outside the method as well because the reference to the original array is passed.

```java
void modifyArray(int[] arr) {
    arr[0] = 100;
}
```

---

## 🛠️ Ways to Create Arrays

There are several ways to create arrays:

```java
int[] arr1;
arr1 = new int[5]; // Declaration then initialization

int[] arr2 = new int[5]; // Declaration and initialization together

int[] arr3 = {1, 2, 3, 4}; // Array literal

int[] arr4 = new int[3];
arr4[0] = 10;
arr4[1] = 20;
arr4[2] = 30;
```

---

## 🧼 Default Values of Arrays

When an array is created, its elements are initialized to default values:

| Data Type     | Default Value |
|---------------|----------------|
| byte, short, int, long | `0` |
| float, double | `0.0` |
| char          | `\u0000` (null character) |
| boolean       | `false` |
| Reference types (e.g., objects, Strings) | `null` |

---

## ⚠️ ArrayIndexOutOfBoundsException

- This is a **runtime exception**.
- Thrown when trying to access an index **less than 0 or greater than or equal to `array.length`**.

Example:

```java
int[] arr = new int[3];
System.out.println(arr[3]); // Throws ArrayIndexOutOfBoundsException
```

---

## 🧩 Anonymous Arrays

- Anonymous arrays are **not assigned to any variable**.
- Often used to pass arrays as method arguments directly when no reuse is needed.

```java
printArray(new int[] {1, 2, 3, 4, 5});
```

---

## 🔃 Sorting Arrays

Java provides built-in sorting through `Arrays.sort()`.

```java
int[] arr = {5, 1, 3, 4, 2};
Arrays.sort(arr); // Sorts in ascending order
```

### Important Notes:

- For **custom objects**, the class must implement `Comparable` interface.
- If not, a `ClassCastException` will occur at runtime.

```java
class Point implements Comparable<Point> {
    int x;
    public int compareTo(Point p) {
        return this.x - p.x;
    }
}
```

---

## 🔍 Searching in Arrays

To find an element in a **sorted array**, use `Arrays.binarySearch()`:

```java
int[] arr = {1, 2, 3, 4, 5};
int index = Arrays.binarySearch(arr, 3); // returns 2
```

- If the element is found → returns the **index**
- If not found → returns **`-(insertionIndex + 1)`** to help maintain the order

---

## 📏 Comparing Arrays

Java provides:

- `==` operator → checks if two arrays refer to the **same object**
- `Arrays.equals(arr1, arr2)` → checks if arrays have the **same elements in the same order**

```java
int[] a = {1, 2};
int[] b = {1, 2};
System.out.println(a == b); // false
System.out.println(Arrays.equals(a, b)); // true
```

---

## 🧯 Filling Arrays

Use `Arrays.fill()` to fill arrays with a value:

```java
int[] arr = new int[5];
Arrays.fill(arr, 3); // All elements become 3 → [3, 3, 3, 3, 3]
```

### Fill with Range:

```java
Arrays.fill(arr, 1, 4, 7);
```

- This fills the array from **index 1 (inclusive) to index 4 (exclusive)**.
- So it affects index 1, 2, and 3.
- **Index 4 is not included**, which is a common source of confusion.

```java
// Resulting array: [3, 7, 7, 7, 3]
```

---

## 🖨️ Printing Arrays

- Standard loops (for, enhanced-for)
- Built-in method:

```java
System.out.println(Arrays.toString(arr)); // Prints [1, 2, 3]
```

---

## 🧵 Variable-Length Argument (Varargs)

- Allows passing **zero or more arguments** to a method.
- Syntax: `void methodName(type... varName)`

### Rules:

1. Only one varargs parameter per method.
2. It **must be the last parameter**.
3. You can still use normal parameters before it.

```java
void printValues(String label, int... numbers) {} // valid

void print(int... nums, String label) {} // INVALID
```

---

## 🧱 Two-Dimensional Arrays

A 2D array is an array of arrays:

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

- `matrix.length` gives the number of rows.
- `matrix[i].length` gives the number of columns in row `i`.

---

## 🪁 Ragged Arrays (Jagged Arrays)

In a ragged array, rows can have **different lengths**.

```java
int[][] ragged = {
    {1, 2, 3},
    {4},
    {5, 6, 7, 8, 9}
};
```

- Each sub-array can be of a different size.
- Useful when the dataset is inherently uneven.

---

*End of Java Array Concepts – Elaborated Version*
