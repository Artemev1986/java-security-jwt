package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserRepositoryTest {

    private final TestEntityManager entityManager;

    private final UserRepository userRepository;

    private final User user = new User();

    @Test
    void findUserByName() {
        user.setName("Mikhail");
        user.setPassword("password");
        entityManager.persist(user);

        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByName(user.getName()));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u)
                                .hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("name", "Mikhail")
                                .hasFieldOrPropertyWithValue("password", "password")
                );
    }

    @Test
    void findUserByNameFailed() {
        user.setName("Mikhail");
        user.setPassword("password");

        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByName(user.getName()));

        assertThat(userOptional).isEmpty();
    }
}