package ru.todo.spring.javadaddy.todoApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String login;
    private String password;
    private String email;
    private String bio;
}
