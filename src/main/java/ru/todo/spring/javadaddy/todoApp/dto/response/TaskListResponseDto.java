package ru.todo.spring.javadaddy.todoApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskListResponseDto {
    private List<TaskResponseDto> tasks;
}
