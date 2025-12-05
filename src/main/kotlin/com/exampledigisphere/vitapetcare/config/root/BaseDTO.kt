package com.exampledigisphere.vitapetcare.config.root

import com.fasterxml.jackson.annotation.JsonView
import java.io.Serial
import java.io.Serializable

open class BaseInput(
  var _edited: Boolean = false,
  var active: Boolean = false,
  var id: Long? = null,
  var uuid: String? = null,
)

open class BaseOutput : Serializable {

  @Serial
  private val serialVersionUID: Long = 1L;

  @field:JsonView(Json.List::class)
  var id: Long? = null

  @field:JsonView(Json.Detail::class)
  var uuid: String? = null

  @field:JsonView(Json.Detail::class)
  var active: Boolean? = null

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

}
