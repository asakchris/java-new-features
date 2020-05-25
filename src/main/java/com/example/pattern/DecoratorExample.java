package com.example.pattern;

import java.util.function.Function;

public class DecoratorExample {
  private static void printIt(Coffee coffee) {
    System.out.println(coffee.prepare(new Ingredient("Hazelnut")));
  }

  public static void main(String[] args) {
    Coffee coffee = new Coffee();
    printIt(coffee);
  }
}

class Coffee {
  private Function<Ingredient, Ingredient> ingredient;

  public Coffee(Function<Ingredient, Ingredient>... ingredients) {
    ingredient = ing -> ing;

    for (Function<Ingredient, Ingredient> aIngredient: ingredients) {
      ingredient = ingredient.andThen(aIngredient);
    }
  }

  public Ingredient prepare(Ingredient input) {
    return ingredient.apply(input);
  }
}

class Ingredient {
  private String name;

  public Ingredient(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}