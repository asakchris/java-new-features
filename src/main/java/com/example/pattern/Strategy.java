package com.example.pattern;

import java.util.List;
import java.util.function.Predicate;

public class Strategy {
  public static void main(String[] args) {
    List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println("total: " + totalValue(numbers, e -> true));
    System.out.println("evenTotal: " + totalValue(numbers, e -> e % 2 == 0));
    System.out.println("oddTotal: " + totalValue(numbers, e -> e % 2 != 0));
  }

  private static int totalValue(List<Integer> numbers, Predicate<Integer> selector) {
    return numbers
        .stream()
        .filter(selector)
        .mapToInt(e -> e)
        .sum();
  }
}
