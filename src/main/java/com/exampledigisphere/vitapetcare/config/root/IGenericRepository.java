package com.exampledigisphere.vitapetcare.config.root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface IGenericRepository<E, ID, F extends FetchSpec> {

  Optional<E> findById(ID id);

  Optional<E> findById(ID id, Set<F> fetches);

  E save(E entity);

  void deleteById(ID id);

  Page<E> findAll(Pageable pageable);
}
