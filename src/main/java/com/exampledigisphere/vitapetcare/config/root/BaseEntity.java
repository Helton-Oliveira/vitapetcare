package com.exampledigisphere.vitapetcare.config.root;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable {
  @Serial
  @Transient
  private Long serialVersionUID = 1L;

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
  private boolean _edited;

  @Transient
  private List<String> _loadedAssociations = new java.util.ArrayList<>();

  public List<String> get_loadedAssociations() {
    if (_loadedAssociations == null) {
      _loadedAssociations = new java.util.ArrayList<>();
    }
    return _loadedAssociations;
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

  public boolean wasEdited() {
    return _edited;
  }

  public void audit(String username) {
    // TODO();
  }

  public Long getSerialVersionUID() {
    return serialVersionUID;
  }

  public void setSerialVersionUID(Long serialVersionUID) {
    this.serialVersionUID = serialVersionUID;
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

  public boolean is_edited() {
    return _edited;
  }

  public void set_edited(boolean _edited) {
    this._edited = _edited;
  }
}
