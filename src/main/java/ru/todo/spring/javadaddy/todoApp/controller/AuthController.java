package ru.todo.spring.javadaddy.todoApp.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.security.JwtUtil;
import ru.todo.spring.javadaddy.todoApp.security.UserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword());

        authManager.authenticate(authInputToken);

        UserDetails user = userDetailsService.loadUserByUsername(authRequestDto.getLogin());
        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponseDto(authRequestDto.getLogin(), token);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody AuthRequestDto authRequestDto) {
        return new AuthResponseDto();
    }
}
