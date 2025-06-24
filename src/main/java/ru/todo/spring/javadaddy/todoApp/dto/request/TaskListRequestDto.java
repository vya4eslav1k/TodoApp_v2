package ru.todo.spring.javadaddy.todoApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.todo.spring.javadaddy.todoApp.enums.SortDirection;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;

@Data
@AllArgsConstructor
public class TaskListRequestDto {
    TaskStatus taskStatusFilter;
    SortDirection taskStatusSortDirection;
    SortDirection dueDateSortDirection;
}
