package com.exampledigisphere.vitapetcare.config.root;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@MappedSuperclass
public class BaseEntity<T extends BaseEntity<T>> implements Serializable {
  @Transient
  private final Long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonView(Json.List.class)
  private Long id;

  @JsonView(Json.Detail.class)
  private String uuid = UUID.randomUUID().toString();

  @JsonView(Json.Detail.class)
  boolean active = true;

  private Instant createdAt = Instant.now();

  private Instant lastModifiedAt = Instant.now();

  private Instant deletedAt;

  private String createdBy;

  private String lastModifiedBy;

  @Transient
  private boolean edited;

  @Transient
  private final Set<String> associations = new HashSet<>();

  public T applyAssociations(Set<String> associations) {
    if (associations != null) {
      this.associations.addAll(associations);
    }
    return (T) this;
  }

  public boolean hasAssociation(String association) {
    return associations.contains(association);
  }

  public BaseEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(Instant lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  public Instant getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(Instant deletedAt) {
    this.deletedAt = deletedAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public boolean isEdited() {
    return edited;
  }

  public void setEdited(boolean edited) {
    this.edited = edited;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface All extends Detail {
    }
  }

  public void disabled() {
    this.active = false;
  }


  public void audit(String username) {
    // TODO();
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || getClass() != object.getClass()) return false;
    BaseEntity<?> that = (BaseEntity<?>) object;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BaseEntity.class.getSimpleName() + "[", "]")
      .add("id=" + id)
      .add("uuid='" + uuid + "'")
      .add("active=" + active)
      .add("createdAt=" + createdAt)
      .add("lastModifiedAt=" + lastModifiedAt)
      .add("deletedAt=" + deletedAt)
      .add("createdBy='" + createdBy + "'")
      .add("lastModifiedBy='" + lastModifiedBy + "'")
      .add("edited=" + edited)
      .toString();
  }
}
