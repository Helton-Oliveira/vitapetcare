package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.auth.roles.RoleAuthorityMapper;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Entity
@Table(name = "usr_users")
public class User extends BaseEntity<User> {

  private String name;
  private String email;
  private String password;
  private String resetCode;
  private Instant createdResetCodeAt;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Transient
  private Set<String> authorities = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @SQLRestriction("active = true")
  private Set<File> files = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @SQLRestriction("active = true")
  private Set<WorkDay> workDays = new HashSet<>();

  public User() {
  }

  public User(Long id, String name, String email, String password, Role role, Boolean edited, Boolean active) {
    this.setId(id);
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.setEdited(edited);
    this.setActive(active);
    this.authorities = RoleAuthorityMapper.getAuthorities(role);

    this.encryptPassword();
    this.prepareForCreation();
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public String getResetCode() {
    return resetCode;
  }

  public Set<File> getFiles() {
    return files;
  }

  public Set<WorkDay> getWorkDays() {
    return workDays;
  }

  public void encryptPassword() {
    if (this.password != null && !this.password.isBlank()) {
      this.password = new BCryptPasswordEncoder().encode(this.password);
    }
  }

  public void prepareForCreation() {
    this.files = null;
    this.workDays = null;
  }

  public void generateResetCode() {
    this.resetCode = new Random().ints(5, 1, 9)
      .mapToObj(String::valueOf)
      .collect(Collectors.joining());
    this.createdResetCodeAt = Instant.now();
    this.setEdited(true);
  }

  public boolean verifyResetCode(String inputResetCode) {
    if (inputResetCode.isBlank() && createdResetCodeAt == null) return false;
    final var expiration = createdResetCodeAt.plusSeconds(1500);
    return resetCode.equals(inputResetCode) && Instant.now().isBefore(expiration);
  }

  public void markResetCodeUsed() {
    this.resetCode = null;
    this.createdResetCodeAt = null;
  }

  public interface Authority {
    String USER_CREATE = "USER_CREATE";
    String USER_EDIT = "USER_EDIT";
    String USER_VIEW = "USER_VIEW";
    String USER_VIEW_LIST = "USER_VIEW_LIST";
    String USER_DELETE = "USER_DELETE";
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
      .add("name='" + name + "'")
      .add("email='" + email + "'")
      .add("password='" + password + "'")
      .add("resetCode='" + resetCode + "'")
      .add("createdResetCodeAt=" + createdResetCodeAt)
      .add("role=" + role)
      .add("authorities=" + authorities)
      //    .add("files=" + files)
      //    .add("workDays=" + workDays)
      .toString();
  }
}
