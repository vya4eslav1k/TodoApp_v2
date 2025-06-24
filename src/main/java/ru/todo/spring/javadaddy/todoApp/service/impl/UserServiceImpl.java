package ru.todo.spring.javadaddy.todoApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.model.User;
import ru.todo.spring.javadaddy.todoApp.repository.UserRepository;
import ru.todo.spring.javadaddy.todoApp.security.JwtUtil;
import ru.todo.spring.javadaddy.todoApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponseDto createAndLoginUser(UserRequestDto userRequestDto) {
        createUser(userRequestDto);
        return jwtUtil.authenticateUser(new AuthRequestDto(userRequestDto.getLogin(), userRequestDto.getPassword()));
    }

    private User createUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .login(userRequestDto.getLogin())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .email(userRequestDto.getEmail())
                .bio(userRequestDto.getBio())
                .build();
        userRepository.save(user);
        return user;
    }

}
