package com.exampledigisphere.vitapetcare.config.root;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Utils {

  public static void required(Object condition, Supplier<String> messageSupplier) {
    if (Objects.isNull(condition)) {
      throw new IllegalArgumentException(messageSupplier.get());
    }
  }

  public static <T> T mapIfRequested(boolean condition, Supplier<T> supplier) {
    return condition ? supplier.get() : null;
  }

  public static <T> T initialize(T entity, Set<? extends AssociationFetcher<T>> fetchers) {
    fetchers.forEach(fetcher -> fetcher.initialize(entity));
    return entity;
  }

  public static Set<String> transformToAssociationSet(Set<? extends Association> associations) {
    return associations.stream()
      .map(Association::getName)
      .collect(Collectors.toSet());
  }
}
