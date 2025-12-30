package com.exampledigisphere.vitapetcare.auth.DTO;

import com.exampledigisphere.vitapetcare.config.root.Info;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Resposta de login"
)
public record LoginResponse(
  String refreshToken,
  String accessToken
) {
}
