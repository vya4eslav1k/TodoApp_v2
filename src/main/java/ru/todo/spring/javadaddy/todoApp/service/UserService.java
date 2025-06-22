package ru.todo.spring.javadaddy.todoApp.service;

import ru.todo.spring.javadaddy.todoApp.dto.request.UserRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.AuthResponseDto;

public interface UserService {
    AuthResponseDto createAndLoginUser(UserRequestDto userRequestDto);
}
