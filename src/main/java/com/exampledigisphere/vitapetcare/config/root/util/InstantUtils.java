package com.exampledigisphere.vitapetcare.config.root.util;

import com.exampledigisphere.vitapetcare.config.root.Info;

import java.time.Instant;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Utilidades para Instant, migrado de InstantExtenstions.kt"
)
public class InstantUtils {
  public static boolean isDeclared(Instant instant, Instant timestamp) {
    return timestamp != null && !timestamp.toString().isBlank();
  }
}
