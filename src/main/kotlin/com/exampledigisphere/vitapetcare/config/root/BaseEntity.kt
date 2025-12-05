package com.exampledigisphere.vitapetcare.config.root

import com.exampledigisphere.vitapetcare.config.root.extensions.isDeclared
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import java.io.Serial
import java.io.Serializable
import java.time.Instant
import java.util.*

@MappedSuperclass
class BaseEntity : Serializable {
  @Serial
  @Transient
  private val serialVersionUID: Long = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @field:JsonView(Json.List::class)
  var id: Long? = null;

  @field:JsonView(Json.Detail::class)
  var uuid: String = UUID.randomUUID().toString();

  @field:JsonView(Json.Detail::class)
  var active: Boolean = true;

  private var createdAt: Instant = Instant.now();

  private var lastModifiedAt: Instant = Instant.now();

  private lateinit var deletedAt: Instant;

  private var createdBy: String? = null;

  private var lastModifiedBy: String? = null;

  @Transient
  private var _edited: Boolean = true;

  @get:Transient
  @Transient
  var _loadedAssociations: MutableSet<String> = mutableSetOf()

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  fun disabled() {
    this.active = false;
  }

  fun wasEdited(): Boolean {
    return _edited;
  }

  fun audit(username: String) {

    this.createdBy
      ?.takeIf { it.isBlank() }
      ?.let { this.createdBy = username; }

    this.createdAt
      .takeIf { !it.isDeclared(it) }
      ?.let { this.createdAt = Instant.now() }

    this.lastModifiedBy = username;
    this.lastModifiedAt = Instant.now();
  }

}
