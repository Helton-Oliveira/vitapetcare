package com.exampledigisphere.vitapetcare.config.root;

import java.util.function.Consumer;
import java.util.function.Function;

public class OptionalUtils {
  public static <T> Function<T, T> peek(Consumer<T> consumer) {
    return t -> {
      consumer.accept(t);
      return t;
    };
  }
}
