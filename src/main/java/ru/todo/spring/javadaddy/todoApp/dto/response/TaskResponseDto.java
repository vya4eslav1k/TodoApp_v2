package ru.todo.spring.javadaddy.todoApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;
import ru.todo.spring.javadaddy.todoApp.model.Task;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
    }
}
