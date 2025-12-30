plugins {
  java
  id("org.springframework.boot") version "3.5.8"
  id("io.spring.dependency-management") version "1.1.7"
}


group = "com.exampledigisphere"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  implementation("org.liquibase:liquibase-core")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  val jjwtVersion = "0.12.6"
  implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

  implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.register<Exec>("buildFrontend") {
  workingDir = file("./")
  commandLine("yarn", "run", "build")
}

tasks.named("bootJar") {
  dependsOn("buildFrontend")
}
