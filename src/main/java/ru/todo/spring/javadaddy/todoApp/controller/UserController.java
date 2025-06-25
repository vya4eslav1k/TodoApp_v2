package ru.todo.spring.javadaddy.todoApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns a JWT token upon successful registration"
    )
    @PostMapping
    public AuthResponseDto createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New user registration data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Registration request example",
                                    value = """
                    {
                      "login": "johndoe",
                      "password": "mysecret",
                      "email": "johndoe@example.com",
                      "bio": "Just a regular user"
                    }
                    """
                            )
                    )
            )
            @RequestBody UserRequestDto userRequestDto
    ) {
        return userService.createAndLoginUser(userRequestDto);
    }
}
