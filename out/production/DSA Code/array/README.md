We need array when we want to store more than one value in a single variable.

It is a collection of elements of the same type stored in a contiguous memory location

Key Characteristics:
1. It is fixed-size, once it is declared it can't be changed.
2. Can access element in an array by index
3. Store elements in contiguous memory location
4. Homogeneous elements - mean stores the same type of element in an array

Benefits of array:
1. Efficient index-based access means O(1) time complexity
2. Optimized for cache performance
3. Better memory management

-> In Java, arrays are passed by reference. This means that when you pass an array to a method, you’re actually passing
a reference to the original array — not a copy. So, if the method modifies any elements of the array, those changes will
also affect the original array outside the method.

-> Different ways to create an array:
1. int[] arr;
arr = new int[10];
2. int[] arr = new int[10];
3. int[] arr = {1,2,3,4,5};
4. int[] arr = new int[3];
arr[0] = 1;
arr[2] = 2;
arr[3] = 3;

-> When an array is created, its elements are assigned by default values:
1. 0 for the numeric primitive data types
2. \u0000 for char type
3. false for boolean type
4. null for reference type, like object, string

-> ArrayIndexOutOfBoundsException: it is a runtime exception in Java that occurs when your code tries to access an
invalid index of an array — either less than 0 or greater than or equal to the array’s length.

-> Anonymous Array:
- Sometime you want to call a method which takes arrays as parameter & once the method's execution finished you
don't want to keep array afterwords.
- It stays in memory until a variable is referenced to it.
- Syntax: printArray(new int[] {1, 2, 3, 4, 5});

-> Sorting Arrays:
- Arrays are sorted using sort() method which is provided by Arrays class. It sorts array in ascending order.
- Syntax: Arrays.sort(arr);, Arrays.sort(arr,fromIndex,toIndex);
- A object never get sorted using sort method which does not implement Comparable interface.
- if you try to sort an array of custom objects that don’t implement Comparable, Java won’t know how to compare
them — and it will throw a ClassCastException at runtime or give a compile-time warning.
For example: Point[] points = {new Point(2, 3),new Point(1, 5),new Point(4, 2)};
Arrays.sort(points); <- This will not work if point doesn't implement comparable

-> Searching Arrays:
- It uses the binarySearch method to find an element in an array. And to use the binarySearch method array must be
sorted.
- Syntax: binarySearch(array,element);
- It will return the index of element if it was found in the array.
- -(insertionIndex+1) if the element was not found and you want to insert that element in an array and want to keep
the array still sorted.

-> Comparing Arrays:use the equals() method to compare two arrays. It will compare the elements of both arrays or object.
if you use == which is equal operator and equal operator works on reference whereas equals method works on value. so if
we use operator does not matter the arrays are identical or not it will always return false.

-> fill arrays: arrays can be filled using fill() method. This method takes two parameters the first parameter is array
and the second is value to be filled in the whole array. For example if you have declared an array int[] arr = new int[10];
and you use the method Arrays.fill(arr,3); so all the 10 element's value will become 3. like int[] arr = {3,3,3,3,3,3,3,3,3,3};
and there is one more method Arrays.fill(arr,value,fromIndex,toIndex) where you can define the range to fill the value in an array.

-> Printing arrays: to print array you can use the for loop, for-each loop, and Arrays.toString(arr) method.

-> Variable-length argument list:
- Only one variable-length parameter may be specified in a method.
- It must be a last parameter
- Any other parameter must come before variable-length argument.
- you can use the variable-length argument when there is no fixed size of array you can have and still you want to
pass the array as a parameter. and to do that you can use void print(int... num). Where '...' is called ellipsis.
- Examples: void print(String... str, double... num) - this will not work
void print(String... str, double num) - it will not work as well
void print(String str, double... num) - this is correct syntax
void print(double... num) - this is also valid

-> In two dimensional array: two dimensional array will look like
{1,2,3}
{1,2,3}
{1,2,3}
which can be declared in syntax like int[][] arr = {{1,2,3},{1,2,3},{1,2,3}}
so to print the arr in the format mentioned above you have to keep one thing in mind that in int[][] the first []
indicates the row and the second [] indicates the column.

Ragged Arrays: In this in each row there will be different number of elements like
int[][] arr = { {1,2,3}
{1}
{1,2,3,4,5,6,7}}

