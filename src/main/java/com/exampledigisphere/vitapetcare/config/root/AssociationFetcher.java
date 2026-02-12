package com.exampledigisphere.vitapetcare.config.root;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Interface functional para busca de associações"
)
@FunctionalInterface
public interface AssociationFetcher<T> {
  void initialize(T entity);
}
