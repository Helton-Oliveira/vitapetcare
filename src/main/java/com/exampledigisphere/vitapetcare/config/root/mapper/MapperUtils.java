package com.exampledigisphere.vitapetcare.config.root.mapper;

import java.util.Set;
import java.util.function.Supplier;

public class MapperUtils {

  /**
   * Executa o mapeamento apenas se a associação estiver presente no Set.
   * * @param loadedAssociations O conjunto de associações carregadas.
   *
   * @param associationName O nome da associação exigida.
   * @param mapping         O bloco de código (Supplier) que retorna o mapeamento.
   * @return O resultado do mapeamento ou null.
   */
  public static <T> T mapIfRequested(
    Set<String> loadedAssociations,
    String associationName,
    Supplier<T> mapping
  ) {
    if (loadedAssociations != null && loadedAssociations.contains(associationName)) {
      return mapping.get();
    }
    return null;
  }
}

