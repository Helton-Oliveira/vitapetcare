package com.exampledigisphere.vitapetcare.config.root;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação obrigatória em todas as classes e métodos públicos.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Info {
  Dev dev();

  Label label();

  String date();

  String description();

  enum Label {
    doc, feature, fix
  }

  enum Dev {
    heltonOliveira
  }
}
