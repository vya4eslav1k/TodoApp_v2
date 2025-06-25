package ru.todo.spring.javadaddy.todoApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    @Operation(
            summary = "Authenticate user",
            description = "Takes login and password and returns a JWT token if authentication is successful"
    )
    @PostMapping
    public AuthResponseDto login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Login request example",
                                    value = """
                    {
                      "login": "johndoe",
                      "password": "mysecret"
                    }
                    """
                            )
                    )
            )
            @RequestBody AuthRequestDto authRequestDto
    ) {
        return jwtUtil.authenticateUser(authRequestDto);
    }
}
