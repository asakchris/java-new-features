package com.example.vo;

import java.util.Objects;

public class Employee {
  private final int id;
  private final String name;
  private final String department;
  private final long salary;

  public Employee(int id, String name, String department, long salary) {
    this.id = id;
    this.name = name;
    this.department = department;
    this.salary = salary;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDepartment() {
    return department;
  }

  public long getSalary() {
    return salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return id == employee.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Employee{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", department='").append(department).append('\'');
    sb.append(", salary=").append(salary);
    sb.append('}');
    return sb.toString();
  }
}
