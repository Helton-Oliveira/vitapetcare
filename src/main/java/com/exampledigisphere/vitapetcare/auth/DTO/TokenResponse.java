package com.exampledigisphere.vitapetcare.auth.DTO;

import com.exampledigisphere.vitapetcare.config.root.Info;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Resposta de atualização de token"
)
public record TokenResponse(
  String accessToken
) {
}
