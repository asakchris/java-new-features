package com.example.collect;

import static com.example.util.Util.createPeople;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.example.vo.Person;
import java.util.List;
import java.util.stream.Stream;

public class FlatMapExample {
  public static void main(String[] args) {
    List<Integer> numbers = List.of(1, 2, 3);

    // One-to-one function
    System.out.println(
        numbers
        .stream()
        .map(e -> e * 2) // One-to-one function Stream<T>.map(f11) ==> Stream<R>
        .collect(toList())
    );

    // One-to-many function
    System.out.println(
        numbers
            .stream()
            .map(e -> List.of(e - 1, e + 1)) // One-to-many function Stream<T>.map(f1n) ==> Stream<List<R>>
            .collect(toList())
    );

    // One-to-many function
    System.out.println(
        numbers
            .stream()
            .flatMap(e -> List.of(e - 1, e + 1).stream()) // One-to-many function Stream<T>.flatMap(f1n) ==> Stream<R>
            .collect(toList())
    );

    // If you have a one-to-one function, use map to go from
    // Stream<T> to Stream <R>

    // If you have a one-to-many function, use map to go from
    // Stream<T> to Stream <Collection<R>>

    // If you have a one-to-many function, use flatMap to go from
    // Stream<T> to Stream <R>

    // All characters in the person name
    System.out.println(
        createPeople()
        .stream()
        .map(Person::getName)
        .flatMap(name -> Stream.of(name.split("")))
        .collect(toSet())
    );

    // Group same age people characters in the name
    System.out.println(
        createPeople()
        .stream()
        .collect(
            groupingBy(
                Person::getAge,
                flatMapping(
                    person -> Stream.of(person.getName().split("")),
                    toSet()
                )
            )
        )
    );

    // Group same age people uppercase characters in the name
    System.out.println(
        createPeople()
            .stream()
            .collect(
                groupingBy(
                    Person::getAge,
                    mapping(
                        person -> person.getName().toUpperCase(),
                        flatMapping(
                            name -> Stream.of(name.split("")),
                            toSet()
                        )
                    )
                )
            )
    );

    // reduce - sum, max, min, reduce, collect
    // collect(Collector)

    // Collectors
    // toList, toSet, toMap
    // toUnmodifiableList, toUnmodifiableSet, toUnmodifiableMap
    // partitioningBy
    // groupingBy
    // groupingBy(Function<T, R>)
    // groupingBy(Function, Collector)
    // mapping(Function, Collector)
    // collectingAndThen(Collector, Function)

    // teeing (Collector, Collector, Operator)
  }
}
