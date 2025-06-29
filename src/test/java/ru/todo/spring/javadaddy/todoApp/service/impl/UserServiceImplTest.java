package ru.todo.spring.javadaddy.todoApp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.model.User;
import ru.todo.spring.javadaddy.todoApp.repository.UserRepository;
import ru.todo.spring.javadaddy.todoApp.security.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createAndLoginUser_createsUserAndReturnsToken() {
        UserRequestDto userRequest = new UserRequestDto("testLogin", "rawPass", "test@example.com", "bio");

        String encodedPassword = "encodedPass";
        AuthResponseDto mockAuthResponse = new AuthResponseDto("testLogin","mock.jwt.token");

        when(passwordEncoder.encode("rawPass")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtUtil.authenticateUser(any(AuthRequestDto.class))).thenReturn(mockAuthResponse);

        AuthResponseDto response = userService.createAndLoginUser(userRequest);

        assertNotNull(response);
        assertEquals("mock.jwt.token", response.getToken());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testLogin", savedUser.getLogin());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("bio", savedUser.getBio());

        verify(jwtUtil).authenticateUser(new AuthRequestDto("testLogin", "rawPass"));
    }
}
