package com.example.util;

import com.example.vo.Employee;
import com.example.vo.Person;
import java.util.List;

public class Util {
  public static List<Person> createPeople() {
    return List.of(
        new Person("Sara", 20),
        new Person("Sara", 22),
        new Person("Bob", 20),
        new Person("Paula", 32),
        new Person("Paul", 32),
        new Person("Jack", 3),
        new Person("Jack", 72),
        new Person("Jill", 11)
    );
  }

  public static List<Person> createPeopleNoDuplicateName() {
    return List.of(
        new Person("Sara", 20),
        new Person("Nancy", 22),
        new Person("Bob", 20),
        new Person("Paula", 32),
        new Person("Paul", 32),
        new Person("Jack", 3),
        new Person("Bill", 72),
        new Person("Jill", 11)
    );
  }

  public static List<Employee> createEmployees() {
    return List.of(
        new Employee(1, "Bob", "HR", 1000),
        new Employee(2, "Jack", "HR", 1500),
        new Employee(3, "Ron", "HR", 1200),
        new Employee(4, "Jill", "IT", 1100),
        new Employee(5, "Bill", "IT", 2200),
        new Employee(6, "Sara", "IT", 1300),
        new Employee(7, "Nancy", "IT", 2500),
        new Employee(8, "Alex", "Sales", 1600),
        new Employee(9, "Josh", "Sales", 1500),
        new Employee(10, "Sam", "Sales", 1600)
    );
  }
}
