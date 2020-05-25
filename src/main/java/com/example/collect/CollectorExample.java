package com.example.collect;

import static com.example.util.Util.createPeople;
import static com.example.util.Util.createPeopleNoDuplicateName;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.vo.Person;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class CollectorExample {
  public static void main(String[] args) {
    // Get the list of names, in uppercase, of those who are older than 30
    List<String> namesOfOlderThan30 = createPeople()
        //.stream()
        .parallelStream()
        .filter(person -> person.getAge() > 30)
        .map(Person::getName)
        .map(String::toUpperCase)
        .collect(toList())
        ;
    System.out.println("namesOfOlderThan30: " + namesOfOlderThan30);

    // Map name as key and age as value
    System.out.println(
        createPeopleNoDuplicateName()
        .stream()
        //.collect(toMap(keyFunction, valueFunction))
        //.collect(toMap(person -> person.getName(), person -> person.getAge()))
        .collect(toMap(Person::getName, Person::getAge))
    );

    // Get all person ages
    // toUnmodifiable...
    System.out.println(
        createPeopleNoDuplicateName()
        .stream()
        .map(Person::getAge)
        //.collect(toList())
        .collect(toUnmodifiableList())
    );

    // Create comma separated name in uppercase of people older than 30
    // joining
    System.out.println(
        createPeopleNoDuplicateName()
        .stream()
        .filter(person -> person.getAge() > 30)
        .map(Person::getName)
        .map(String::toUpperCase)
        .collect(joining(", "))
    );

    // Collector<T, A, R>

    // Group the people based on their age even or odd
    // partitioningBy
    Map<Boolean, List<Person>> evenOdd = createPeople()
        .stream()
        .collect(partitioningBy(person -> person.getAge() % 2 == 0))
        ;
    System.out.println("evenOdd: " + evenOdd);

    // Group the people based on their name
    // groupingBy
    Map<String, List<Person>> nameList = createPeople()
        .stream()
        //.collect(groupingBy(person -> person.getName()))
        .collect(groupingBy(Person::getName))
        ;
    System.out.println("nameList: " + nameList);

    // Group the people based on their name and get their age
    // groupingBy(Function<T, R>) ===> Collector
    // groupingBy(Function<T, R>, Collector) ===> Collector
    Map<String, List<Integer>> nameAges = createPeople()
        .stream()
        .collect(
            // Collector(Function, Collector(Function, Collector))
            groupingBy(
                Person::getName,
                mapping(
                    Person::getAge,
                    toList()
                )
            )
        )
        ;
    System.out.println("nameAges: " + nameAges);

    // Count people by name
    // counting
    Map<String, Long> countByName = createPeople()
        .stream()
        .collect(
            groupingBy(
                Person::getName,
                counting()
            )
        );
    System.out.println("countByName: " + countByName);

    // Count people by name in int
    // groupingBy and mapping  (Function, Collector)
    // collectingAndThen       (Collector, Function)
    Map<String, Integer> countByNameInt = createPeople()
        .stream()
        .collect(
            groupingBy(
                Person::getName,
                collectingAndThen(
                    counting(),
                    Long::intValue
                )
            )
        );
    System.out.println("countByNameInt: " + countByNameInt);

    // Total age
    System.out.println(
        createPeople()
        .stream()
        .map(Person::getAge)
        .reduce(0, (total, age) -> total + age)
    );
    System.out.println(
        createPeople()
            .stream()
            .mapToInt(Person::getAge)
            .sum()
    );
    // reduce - reduce, collect, sum

    // Maximum age
    OptionalInt maxAge = createPeople()
        .stream()
        .mapToInt(Person::getAge)
        .max();
    System.out.println("maxAge: " + maxAge);

    // Person with maximum age
    System.out.println(
        createPeople()
        .stream()
        .collect(maxBy(comparing(Person::getAge)))
    );

    // Person with minimum age
    System.out.println(
        createPeople()
            .stream()
            .collect(minBy(comparing(Person::getAge)))
    );

    // Name of the person with maximum age
    String maxAgePersonName = createPeople()
        .stream()
        .collect(
            collectingAndThen(
                maxBy(comparing(Person::getAge)),
                personOptional -> personOptional.map(Person::getName).orElse("")
            )
        );
    System.out.println("maxAgePersonName: " + maxAgePersonName);

    // map vs mapping
    // map ==> transformation in the stream
    // mapping ==> transformation in the middle of reduce

    // filter vs filtering
    // filter ==> filter in the stream
    // filtering ==> filtering in the middle of reduce

    // Group same age people name
    System.out.println(
        createPeople()
        .stream()
        .collect(
            groupingBy(
                Person::getAge,
                mapping(
                    Person::getName,
                    toList()
                )
            )
        )
    );

    // Group same age people name if name is greater than 3 characters
    System.out.println(
        createPeople()
            .stream()
            .collect(
                groupingBy(
                    Person::getAge,
                    mapping(
                        Person::getName,
                        filtering(
                            name -> name.length() > 3,
                            toList()
                        )
                    )
                )
            )
    );

    // teeing - combine two collectors
    // grouping mapping  ===> (Function, Collector)
    // collectingAndThen ===> (Collector, Function)
    // teeing            ===> (Collector, Collector, operation)
  }
}
