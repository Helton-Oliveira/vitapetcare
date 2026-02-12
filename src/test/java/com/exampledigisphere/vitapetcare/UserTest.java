package com.exampledigisphere.vitapetcare;

import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserFactory;
import com.exampledigisphere.vitapetcare.admin.user.UserService;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("Nao deve retornar associacoes do usuario caso nao sejam solicitadas")
  public void cannotReturnAssociationsIfNotRequested() {
    final var user = userService.retrieve(1L, Collections.emptySet())
      .orElseThrow();

    System.out.println(user.getFiles());

    assertThat(user.getFiles()).isNullOrEmpty();
  }

  @Test
  @DisplayName("Deve retornar associacoes do usuario quando solicitadas")
  public void ReturnAssociationsRequested() {
    final var user = userService.retrieve(1L, Set.of(UserAssociations.FILES))
      .orElseThrow();

    assertThat(user.getFiles().size()).isEqualTo(1);
    assertThat(user.getWorkDays()).isNullOrEmpty();
  }

  @Test
  @DisplayName("O Mapper não deve processar coleções se a condição for falsa")
  public void mapperShouldNotProcessCollections() {
    User user = new User();
    UserDTO dto = UserFactory.toResponse(user);

    assertThat(dto.getFiles()).isNullOrEmpty();
  }

  @Test
  @DisplayName("Mapper não deve disparar carga de coleções")
  public void mapperShouldNotTriggerLoading() {
    User user = userRepository.findById(1L).orElseThrow();

    assertThat(Hibernate.isInitialized(user.getFiles())).isFalse();

    UserFactory.toResponse(user);

    assertThat(Hibernate.isInitialized(user.getFiles()))
      .as("O mapper acessou a coleção indevidamente")
      .isFalse();
  }

}
