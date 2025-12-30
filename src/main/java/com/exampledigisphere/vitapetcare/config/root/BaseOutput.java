package com.exampledigisphere.vitapetcare.config.root;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serial;
import java.io.Serializable;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Contrato base para DTOs de saída"
)
public class BaseOutput implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonView(Json.List.class)
  private Long id;

  @JsonView(Json.Detail.class)
  private String uuid;

  @JsonView(Json.Detail.class)
  private Boolean active;

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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface All extends Detail {
    }
  }
}
