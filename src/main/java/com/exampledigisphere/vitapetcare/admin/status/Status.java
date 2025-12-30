package com.exampledigisphere.vitapetcare.admin.status;

import com.exampledigisphere.vitapetcare.config.root.Info;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Enumeração de status globais do sistema"
)
public enum Status {
  ACTIVE,
  INACTIVE,
  IN_PROGRESS,
  CONFIRM,
  DECLINED,
  NOT_INFORMED
}
