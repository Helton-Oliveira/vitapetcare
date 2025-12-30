package com.exampledigisphere.vitapetcare.config.root;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Contrato base para DTOs de entrada"
)
public record BaseInput(
  Boolean _edited,
  Boolean active,
  Long id,
  String uuid
) {
  public BaseInput() {
    this(false, false, null, null);
  }
}
