package ru.todo.spring.javadaddy.todoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    private AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return jwtUtil.authenticateUser(authRequestDto);
    }
}
