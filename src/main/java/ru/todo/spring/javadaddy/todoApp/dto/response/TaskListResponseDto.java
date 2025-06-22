package ru.todo.spring.javadaddy.todoApp.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TaskListResponseDto {
    private List<TaskResponseDto> tasks;
}
