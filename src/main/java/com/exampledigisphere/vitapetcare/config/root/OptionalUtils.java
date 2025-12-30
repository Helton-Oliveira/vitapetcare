package com.exampledigisphere.vitapetcare.config.root;

import java.util.function.Consumer;
import java.util.function.Function;

public final class OptionalUtils {

  private OptionalUtils() {
  }

  public static <T> Function<T, T> peek(Consumer<T> consumer) {
    return value -> {
      consumer.accept(value);
      return value;
    };
  }
}

