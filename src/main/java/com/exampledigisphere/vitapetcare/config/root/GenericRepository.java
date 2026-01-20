package com.exampledigisphere.vitapetcare.config.root;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public abstract class GenericRepository<E, ID, F extends FetchSpec> implements IGenericRepository<E, ID, F> {

  @PersistenceContext
  protected EntityManager em;

  protected final Class<E> entityClass;

  public GenericRepository(Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<E> findById(ID id) {
    return Optional.ofNullable(em.find(entityClass, id));
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<E> findById(ID id, Set<F> fetches) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<E> cq = cb.createQuery(entityClass);
    Root<E> root = cq.from(entityClass);

    fetches.forEach(fetch ->
      root.fetch(fetch.attribute(), JoinType.LEFT)
    );

    cq.select(root)
      .where(cb.equal(root.get("id"), id))
      .distinct(true);

    List<E> results = em.createQuery(cq)
      .setMaxResults(1)
      .getResultList();

    return results.isEmpty()
      ? Optional.empty()
      : Optional.of(results.getFirst());
  }

  @Override
  public E save(E entity) {
    return em.merge(entity);
  }


  @Override
  public void deleteById(ID id) {
    findById(id).ifPresent(entity -> em.remove(
      em.contains(entity) ? entity : em.merge(entity)
    ));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<E> findAll(Pageable pageable) {

    CriteriaBuilder cb = em.getCriteriaBuilder();

    CriteriaQuery<E> cq = cb.createQuery(entityClass);
    Root<E> root = cq.from(entityClass);
    cq.select(root);

    TypedQuery<E> query = em.createQuery(cq);
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<E> content = query.getResultList();

    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    Root<E> countRoot = countQuery.from(entityClass);
    countQuery.select(cb.count(countRoot));

    Long total = em.createQuery(countQuery).getSingleResult();

    return new PageImpl<>(content, pageable, total);
  }
}

