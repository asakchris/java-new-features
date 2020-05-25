package com.example.pattern;

import java.util.function.Function;

public class Decorator {
  public static void main(String[] args) {
    Function<Integer, Integer> inc = e -> e + 1;

    printIt(5, "incremented", inc);
    printIt(10, "incremented", inc);

    Function<Integer, Integer> doubled = e -> e * 2;

    printIt(5, "doubled", doubled);
    printIt(10, "doubled", doubled);

    printIt(5, "incremented & doubled", inc.andThen(doubled));
    printIt(10, "incremented & doubled", inc.andThen(doubled));
  }

  private static void printIt(int n, String message, Function<Integer, Integer> func) {
    System.out.println(n + " " + message + ": " + func.apply(n));
  }
}
