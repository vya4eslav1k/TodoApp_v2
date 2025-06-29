package ru.todo.spring.javadaddy.todoApp.controller.openApiConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;

public interface UserControllerOpenApiConfig {
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns a JWT token upon successful registration"
    )
    AuthResponseDto createUser(
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
            UserRequestDto userRequestDto
    );
}
