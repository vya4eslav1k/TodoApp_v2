package ru.todo.spring.javadaddy.todoApp.dto.request;

import lombok.Data;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;

import java.time.LocalDate;

@Data
public class TaskCreateRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
