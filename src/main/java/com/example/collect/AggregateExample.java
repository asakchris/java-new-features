package com.example.collect;

import static com.example.util.Util.createEmployees;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

import com.example.vo.Employee;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;

public class AggregateExample {
  public static void main(String[] args) {
    Map<String, List<Employee>> employeesGroupByDepartment =
        createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment
            )
        )
        ;
    System.out.println("employeesGroupByDepartment: " + employeesGroupByDepartment);

    Map<String, Optional<Employee>> maxSalaryEmployeeByDepartment = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                maxBy(
                    comparing(Employee::getSalary)
                )
            )
        )
        ;
    System.out.println("maxSalaryEmployeeByDepartment: " + maxSalaryEmployeeByDepartment);

    Map<String, Optional<Long>> maxSalaryByDepartment = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                collectingAndThen(
                    maxBy(
                        comparing(Employee::getSalary)
                    ),
                    empOptional -> empOptional.map(Employee::getSalary)
                )
            )
        )
        ;
    System.out.println("maxSalaryByDepartment: " + maxSalaryByDepartment);

    Map<String, Optional<String>> maxSalaryEmployeeNameByDepartment = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                collectingAndThen(
                    maxBy(
                        comparing(Employee::getSalary)
                    ),
                    empOptional -> empOptional.map(Employee::getName)
                )
            )
        )
        ;
    System.out.println("maxSalaryEmployeeNameByDepartment: " + maxSalaryEmployeeNameByDepartment);

    Map<String, List<Employee>> groupByDepartmentAndSortBySalary = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                collectingAndThen(
                    toList(),
                    employees -> employees
                        .stream()
                        .sorted(
                            comparing(Employee::getSalary)
                        )
                        .collect(toList())
                )
            )
        )
        ;
    System.out.println("groupByDepartmentAndSortBySalary: " + groupByDepartmentAndSortBySalary);

    Map<String, List<Employee>> groupByDepartmentAndSortBySalaryDesc = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                collectingAndThen(
                    toList(),
                    employees -> employees
                        .stream()
                        .sorted(
                            comparing(Employee::getSalary)
                                .reversed()
                        )
                        .collect(toList())
                )
            )
        )
        ;
    System.out.println("groupByDepartmentAndSortBySalaryDesc: " + groupByDepartmentAndSortBySalaryDesc);

    // This will not work if there are tie in top 2 rank
    Map<String, List<Employee>> groupByDepartmentAndSortBySalaryTop2 = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                collectingAndThen(
                    toList(),
                    employees -> employees
                        .stream()
                        .sorted(
                            comparing(Employee::getSalary)
                        )
                        .limit(2)
                        .collect(toList())
                )
            )
        )
        ;
    System.out.println("groupByDepartmentAndSortBySalaryTop2: " + groupByDepartmentAndSortBySalaryTop2);

    Map<String, SortedMap<Integer, List<Employee>>> groupByDepartmentAndSortBySalaryRank = createEmployees()
        .stream()
        .collect(
            groupingBy(
                Employee::getDepartment,
                rankingCollector(
                    Employee::getSalary,
                    naturalOrder()
                )
            )
        )
        ;
    System.out.println("groupByDepartmentAndSortBySalaryRank: " + groupByDepartmentAndSortBySalaryRank);
  }

  /*private static <T, V> Collector<T, ?, SortedMap<Integer, List<T>>> ranking(Function<T, V> rankProperty, Comparator<V> comparator) {
    return collectingAndThen(
        groupingBy(
            rankProperty,
            () -> new TreeMap<>(comparator),
            toList()
        ),
        map -> map
            .entrySet()
            .stream()
            .collect(
                TreeMap::new,
                (rank, entry) -> rank.put(
                    rank.isEmpty() ? 1 : rank.lastKey() + rank.get(rank.lastKey()).size(),
                    entry.getValue()
                )
            )
    );
  }*/

  static <T, V> Collector<T, ?, SortedMap<Integer, List<T>>> rankingCollector(
      Function<T, V> propertyExtractor, Comparator<V> propertyComparator) {
    return collectingAndThen(
        groupingBy(
            propertyExtractor,
            () -> new TreeMap<>(propertyComparator), toList()
        ),
        map ->
            map
                .entrySet()
                .stream()
                .collect(
                    TreeMap::new,
                    (rank, entry) ->
                        rank.put(
                            rank.isEmpty() ? 1 : rank.lastKey() + rank.get(rank.lastKey()).size(),
                            entry.getValue()
                        ),
                    (rank1, rank2) -> {
                      int lastRanking = rank1.lastKey();
                      int offset = lastRanking + rank1.get(lastRanking).size() - 1;
                      if (propertyExtractor.apply(rank1.get(lastRanking).get(0))
                          == propertyExtractor.apply(rank2.get(rank2.firstKey()).get(0))) {
                        rank1.get(lastRanking).addAll(rank2.get(rank2.firstKey()));
                        rank2.remove(rank2.firstKey());
                      }
                      rank2.forEach(
                          (r, items) -> {
                            rank1.put(offset + r, items);
                          });
                    }
                )
    )
    ;
  }
}
