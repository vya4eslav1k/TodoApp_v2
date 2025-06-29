package ru.todo.spring.javadaddy.todoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.todo.spring.javadaddy.todoApp.controller.openApiConfig.UserControllerOpenApiConfig;
import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;
import ru.todo.spring.javadaddy.todoApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerOpenApiConfig {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public AuthResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createAndLoginUser(userRequestDto);
    }
}
