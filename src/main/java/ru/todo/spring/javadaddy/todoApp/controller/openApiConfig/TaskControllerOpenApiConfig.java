package ru.todo.spring.javadaddy.todoApp.controller.openApiConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskCreateRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskFindRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskListResponseDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskResponseDto;
import ru.todo.spring.javadaddy.todoApp.enums.SortDirection;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

public interface TaskControllerOpenApiConfig {
    @Operation(
            summary = "Get tasks for the authenticated user",
            description = "Returns a list of tasks with optional filtering and sorting by status or due date"
    )
    TaskListResponseDto getTasks(
            @Parameter(
                    description = "Optional filter by task status (e.g., TODO, IN_PROGRESS, DONE)",
                    example = "TODO"
            )
            TaskStatus taskStatusFilter,

            @Parameter(
                    description = "Optional sort direction for task status (ASC or DESC)",
                    example = "ASC"
            )
            SortDirection taskStatusSortDirection,

            @Parameter(
                    description = "Optional sort direction for due date (ASC or DESC)",
                    example = "DESC"
            )
            SortDirection dueDateSortDirection
    );

    @Operation(
            summary = "Get task by ID",
            description = "Returns a task by its ID if it belongs to the authenticated user"
    )
    TaskResponseDto getTaskById(
            @Parameter(
                    description = "ID of the task to retrieve",
                    example = "2"
            )
            Integer id
    ) throws AccessDeniedException;

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task for the authenticated user"
    )
    @Parameters({
            @Parameter(
                    name = "taskCreateRequestDto",
                    description = "Task data to be created",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TaskCreateRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "title": "myFirstTask",
                  "description": "my task description",
                  "dueDate": "2025-06-30",
                  "status": "TODO"
                }
            """)
                    )
            )
    })
    TaskResponseDto createTask(TaskCreateRequestDto taskCreateRequestDto);

    @Operation(
            summary = "Delete a task by ID",
            description = "Deletes a task by its ID. Only the task owner is allowed to delete it."
    )
    ResponseEntity<?> deleteTaskById(Integer id) throws AccessDeniedException;
}
