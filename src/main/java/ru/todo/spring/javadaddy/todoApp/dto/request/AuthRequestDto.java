package ru.todo.spring.javadaddy.todoApp.dto.request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}
