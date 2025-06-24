package ru.todo.spring.javadaddy.todoApp.service;

import ru.todo.spring.javadaddy.todoApp.dto.request.TaskCreateRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskFindRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskListRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskListResponseDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskResponseDto;

import java.nio.file.AccessDeniedException;


public interface TaskService {
    TaskListResponseDto getTasks(TaskListRequestDto taskDto);
    TaskResponseDto getTaskById(TaskFindRequestDto taskDto) throws AccessDeniedException;
    TaskResponseDto addTask(TaskCreateRequestDto taskDto);
    void deleteTaskById(TaskFindRequestDto taskDto) throws AccessDeniedException;
}
