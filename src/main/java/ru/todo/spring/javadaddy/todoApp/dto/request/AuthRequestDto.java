package ru.todo.spring.javadaddy.todoApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDto {
    private String login;
    private String password;
}
