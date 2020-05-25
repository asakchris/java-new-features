## Optional
- May or may not have an object
- Optional may have 0 or 1 value, whereas Stream may have 0, 1, or more values
- Use Optional<T> as return type, not as a parameter to a function
- Instead of using Optional as parameter to a function, use overloading
```java
public static <T> void process(T t)
public static void process()
```
- Don't use `Optional.get()` method, it throws `NoSuchElementException` if Optional is empty
- Use `Optional.ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)` ===> Since Java 9
```
// Optional<T>                          Present         Absent
// orElse(substitute)                   T data          T substitute
// or(() -> Optional.of(substitute))    Optional(data)  Optional(substitute)
```
- Use `Optional.stream()` to stream value in Optional