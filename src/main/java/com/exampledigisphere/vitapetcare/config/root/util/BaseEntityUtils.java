package com.exampledigisphere.vitapetcare.config.root.util;

import com.exampledigisphere.vitapetcare.config.root.AssociationFetcher;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Utilidades para BaseEntity, migrado de BaseEntityExtensions.kt"
)
public class BaseEntityUtils {

  public static <E extends BaseEntity, T> T mapIfRequested(E entity, String associationName, Supplier<T> mapping) {
    if (entity.get_loadedAssociations().contains(associationName)) {
      return mapping.get();
    }
    return null;
  }

  public static <E extends BaseEntity> E applyFetches(E entity, Set<AssociationFetcher> associations) {
    entity.get_loadedAssociations().addAll(
      associations.stream()
        .map(AssociationFetcher::getPropertyName)
        .collect(Collectors.toSet())
    );
    return entity;
  }
}
