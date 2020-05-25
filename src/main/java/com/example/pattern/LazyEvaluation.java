package com.example.pattern;

import java.util.function.Supplier;

public class LazyEvaluation {
  public static void main(String[] args) {
    int x = 4;
    var temp = new Lazy<Integer>(() -> compute(x));

    if (x > 5 && temp.get() > 7) {
      System.out.println("Path 1");
    } else {
      System.out.println("Path 2");
    }
  }

  private static int compute(int n) {
    System.out.println("called...");
    return n * 2;
  }
}

class Lazy<T> {
  private T instance;
  private Supplier<T> supplier;

  public Lazy(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public T get() {
    if (instance == null) {
      instance = supplier.get();
      supplier = null;
    }
    return instance;
  }
}