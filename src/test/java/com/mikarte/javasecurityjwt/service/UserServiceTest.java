package com.mikarte.javasecurityjwt.service;

import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.mapper.UserMapper;
import com.mikarte.javasecurityjwt.model.User;
import com.mikarte.javasecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {

    private final UserService userService;

    @MockBean
    private final UserRepository userRepository;

    private final User user = new User();

    @BeforeEach
    void beforeEach() {
        user.setId(1L);
        user.setName("Mikhail");
        user.setPassword("password");
    }

    @Test
    void addUser() {
        Mockito
                .when(userRepository.save(any()))
                .thenReturn(user);

        UserDto userDto = userService.addUser(user);

        assertThat(userDto).isEqualTo(UserMapper.toUserDto(user));
    }

    @Test
    void findByName() {
        Mockito
                .when(userRepository.findUserByName(anyString()))
                .thenReturn(user);

        User userFromMemory = userService.findByName(user.getName());

        assertThat(userFromMemory).isEqualTo(user);
    }

    @Test
    void findByNameFailed() {
        Mockito
                .when(userRepository.findUserByName(anyString()))
                .thenThrow(new EntityNotFoundException("User named " + user.getName() + " not found"));

        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByName(user.getName()));

        assertThat("User named " + user.getName() + " not found")
                .isEqualTo(exception.getMessage());
    }

    @Test
    void findByLoginAndPassword() {
        Mockito
                .when(userRepository.findUserByName(anyString()))
                .thenReturn(user);

        User userFromMemory = userService.findByLoginAndPassword(user.getName(), user.getPassword());

        assertThat(userFromMemory).isEqualTo(user);
    }

    @Test
    void findByLoginAndPasswordFailed() {
        Mockito
                .when(userRepository.findUserByName(anyString()))
                .thenReturn(user);

        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByLoginAndPassword(user.getName(), "wrong password"));

        assertThat("There is no " + user.getName() + " in the database with this password")
                .isEqualTo(exception.getMessage());
    }
}