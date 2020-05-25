package com.example.collect;

import static com.example.util.Util.createPeople;
import static java.util.Comparator.comparing;

import com.example.vo.Person;
import java.util.ArrayList;
import java.util.List;

public class ReduceExample {
  public static void main(String[] args) {
    // Print all persons
    createPeople()
        .forEach(System.out::println)
    ;

    // Sort person by name
    createPeople()
        .stream()
        .sorted(
            comparing(Person::getName)
        )
        .forEach(System.out::println);

    // Sort person by age & name
    createPeople()
        .stream()
        .sorted(
            comparing(Person::getAge)
            .thenComparing(Person::getName)
        )
        .forEach(System.out::println);

    // Print person age greater than 30
    createPeople()
        .stream()
        .filter(person -> person.getAge() > 30)
        .forEach(System.out::println)
    ;

    // Print all person name
    createPeople()
        .stream()
        .map(Person::getName)
        .forEach(System.out::println);

    // Total age of all persons
    System.out.println(
        createPeople()
            .stream()
            .map(Person::getAge)
            //.reduce(0, (total, age) -> total + age)
            //.reduce(0, (total, age) -> Integer.sum(total, age))
            .reduce(0, Integer::sum)
    );
    // Reduce takes the collection, reduces to a single value
    // Reduce converts a Stream to something concrete
    // Java has reduce in 2 forms: reduce & collect

    // Object Oriented Programming: Polymorphism

    // Functional Programming: Composition + Lazy Evaluation
    // Lazy evaluation requires purity of function (side effect)
    // Pure function returns the same result any number of times we call it with same input - idempotent
    // Pure function don't have side effect:
    // 1. Pure function do not change anything
    // 2. Pure function do not depend on anything that may possibly change

    // Get the list of names, in uppercase, of those who are older than 30
    List<String> namesOfOlderThan30 = createPeople()
        //.stream()
        .parallelStream()
        .filter(person -> person.getAge() > 30)
        .map(Person::getName)
        .map(String::toUpperCase)
        .reduce(
            // Identity
            new ArrayList<String>(),
            // Collect
            (names, name) -> {
              names.add(name);
              return names;
            },
            // Collect sub objects when it runs in parallel
            (names1, names2) -> {
              names1.addAll(names2);
              return names1;
            }
        );
    System.out.println("namesOfOlderThan30: " + namesOfOlderThan30);
  }
}
