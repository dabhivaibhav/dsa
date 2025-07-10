
# Java Generics: Complete and Structured Guide

Generics is a powerful feature introduced in **Java 5** as part of the Java Collections Framework. It allows **type safety**, **code reusability**, and **compile-time checking** of types.

---

## 🔰 What is Generics?

Generics allow you to **define classes, interfaces, and methods with type parameters**. Instead of working with raw types, you can enforce the type of object the structure can work with.

---

## ❓ Why Use Generics?

Before generics, Java used raw types and typecasting, which led to **ClassCastException** at runtime.

```java
List list = new ArrayList();
list.add("Hello");
Integer num = (Integer) list.get(0); // Throws ClassCastException at runtime
```

With Generics:

```java
List<String> list = new ArrayList<>();
list.add("Hello");
String str = list.get(0); // Safe, no casting needed
```

---

## ✅ Benefits of Generics

1. **Type Safety** – Detects incompatible types at compile-time.
2. **Code Reusability** – Write a single class or method that works with different types.
3. **Eliminates Type Casting** – No need for explicit type casting.
4. **Generic Algorithms** – You can write methods/classes that work for any object type.

---

## 📦 Syntax of Generics

```java
class Box<T> {
    private T value;
    public void set(T value) { this.value = value; }
    public T get() { return value; }
}
```

---

## 🧪 Using Generic Classes

```java
Box<Integer> intBox = new Box<>();
intBox.set(100);
System.out.println(intBox.get());

Box<String> strBox = new Box<>();
strBox.set("Generics");
System.out.println(strBox.get());
```

---

## 🧮 Generic Methods

```java
public class GenericUtils {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
```

Usage:

```java
Integer[] intArr = {1, 2, 3};
String[] strArr = {"A", "B", "C"};

GenericUtils.printArray(intArr);
GenericUtils.printArray(strArr);
```

---

## 🔠 Bounded Type Parameters

Sometimes you want to restrict the types that can be used with generics.

```java
class NumberBox<T extends Number> {
    T num;
}
```

This means `T` can be `Integer`, `Double`, `Float`, etc., but **not** `String` or custom types.

---

## 🔄 Multiple Type Parameters

You can use more than one type parameter:

```java
class Pair<K, V> {
    private K key;
    private V value;
}
```

Usage:

```java
Pair<String, Integer> pair = new Pair<>();
```

---

## ⏳ Wildcards in Generics

Wildcards allow unknown types. Represented by `?`.

### 1. **Unbounded Wildcard `<?>`**

Accepts any type:

```java
public void printList(List<?> list) {
    for (Object obj : list) {
        System.out.println(obj);
    }
}
```

### 2. **Upper Bounded Wildcard `<? extends T>`**

Allows type T or its subclasses:

```java
public void process(List<? extends Number> list) {}
```

### 3. **Lower Bounded Wildcard `<? super T>`**

Allows type T or its superclasses:

```java
public void addNumbers(List<? super Integer> list) {}
```

---

## 🚫 Limitations of Generics

1. **Cannot use primitives** directly (`List<int>` is not allowed — use `List<Integer>`).
2. **No runtime type information** due to **type erasure**.
3. **Cannot create instances** of a generic type (`new T()` not allowed).
4. **Static context** cannot access type parameters.

---

## 🔄 Type Erasure in Generics

At runtime, all generic type information is **erased**. This is called **Type Erasure**.

```java
List<String> a = new ArrayList<>();
List<Integer> b = new ArrayList<>();

System.out.println(a.getClass() == b.getClass()); // true
```

---

## 🧵 Generic Interfaces

```java
interface Processor<T> {
    void process(T value);
}

class StringProcessor implements Processor<String> {
    public void process(String value) {
        System.out.println("Processing: " + value);
    }
}
```

---

## 🧰 Generic Constructors

```java
class Demo {
    <T> Demo(T data) {
        System.out.println(data);
    }
}
```

---

## 🧪 Best Practices

- Use **`T`, `E`, `K`, `V`, N`** for common type parameter naming.
- Prefer **bounded wildcards** when only reading or writing.
- Don't overuse generics — use when type flexibility is needed.

---

## ✅ Summary

| Feature                 | Description                                           |
|-------------------------|-------------------------------------------------------|
| Type Safety             | Detects errors at compile time                        |
| Type Erasure            | Removes type info at runtime                          |
| Generic Classes         | Classes that work with any type                       |
| Generic Methods         | Methods that work with any type                       |
| Bounded Parameters      | Restrict generic types to subclass or superclass      |
| Wildcards               | For flexibility with unknown types                    |
| Cannot Use Primitives   | Must use wrapper types                                |
| Cannot Create new T()   | Instantiation is not allowed                          |

---
