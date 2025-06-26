-> When to use ArrayList?
 - So when we need dynamic sizing
 - when we need random fast access, because arraylist provide element access by index so it is getting done within O(1).
 - When we need frequent read operation but fewer insertion/deletion operation
 - when we need to work on objects as arraylist allow objects no primitive types

-> ArrayList is implementation of the List Interface. It implements all operations and permits all values including null.
The size, isEmpty, get, set, iterator, and listIterator operations run in constant time O(1). and all the other operation
run in linear time O(n). The add operation takes almost O(1) time complexity for example if  the arraylist is of size 10
so adding the elements in that is O(1) but when resizing happens upon adding 11th element it runs slow which is O(n).
But if you take out the average per each add operation it is almost O(1).

-> An application can increase the capacity of an ArrayList instance before adding a large number of elements using the ensureCapacity operation. This may reduce the amount of incremental reallocation.

-> `Syntax: ArrayList<Integer> integerArrayList = new ArrayList<>();`

-> we can insert the element using the add() method in arraylist.
For example: 
`ArrayList<String> stringArrayList = new ArrayList<>();
 stringArrayList.add("ABC");
 stringArrayList.add("DEF");
 stringArrayList.add("XYZ");
 sout(stringArray) // [ABC, DEF, XYZ]`
here it prints the content of the arraylist as it overrides the toString() method to print its element.
Similarly, we can use add(index, element) where it takes the index of the element needs to be inserted.

-> To get the value of element at specified index we can use get() method.
For example to find DEF from the above example need to type `sout(stringArrayList.get(1));`

-> When we need to change the element or value in an arraylist we can use set() method. this method takes two parameters
like index and the element need to be inserted. For example if we want to change the XYZ to GHI then we need to type code
`stringArrayList.set(2, "GHI");`.
Note: Strings are immutable. so when we change the value what happens behind the scene is that earlier the 2nd index was 
referencing to XYZ first and now the reference will be changed to GHI. As far as the string has an object which reference 
it, it stays in the memory if there is no object is referencing the string then it will be removed and collected by the
java garbage collector.

-> To remove an element or a value we have two ways. First way is to remove element by index and the other ways is to
remove using value. for example: `stringArrayList.remove(1) or stringArrayList.remove("DEF")` which removes the DEF
element from the arraylist.
Note: if you want to remove the element by value then use `Integer.valueOf(variable)` because if we do not use this
there might be a chance that the value you entered is matching with index and instead of removing a value.
For example: you want to remove a value 2 which is located at 10 and it remove the value at 2nd index.

-> To remove all the elements use the clear() method

-> To get the size of the arraylist means length of it use the `size()` method.



