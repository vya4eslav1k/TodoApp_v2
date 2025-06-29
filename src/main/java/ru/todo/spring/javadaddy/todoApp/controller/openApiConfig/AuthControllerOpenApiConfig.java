package ru.todo.spring.javadaddy.todoApp.controller.openApiConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import ru.todo.spring.javadaddy.todoApp.dto.request.AuthRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;

public interface AuthControllerOpenApiConfig {
    @Operation(
            summary = "Authenticate user",
            description = "Takes login and password and returns a JWT token if authentication is successful"
    )
     AuthResponseDto login(
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
            AuthRequestDto authRequestDto
    );
}
