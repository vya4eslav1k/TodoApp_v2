package ru.todo.spring.javadaddy.todoApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskCreateRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskFindRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskListRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskListResponseDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskResponseDto;
import ru.todo.spring.javadaddy.todoApp.enums.SortDirection;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;
import ru.todo.spring.javadaddy.todoApp.service.TaskService;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Get tasks for the authenticated user",
            description = "Returns a list of tasks with optional filtering and sorting by status or due date"
    )
    @GetMapping
    public TaskListResponseDto getTasks(
            @Parameter(
                    description = "Optional filter by task status (e.g., TODO, IN_PROGRESS, DONE)",
                    example = "TODO"
            )
            @RequestParam(required = false) TaskStatus taskStatusFilter,

            @Parameter(
                    description = "Optional sort direction for task status (ASC or DESC)",
                    example = "ASC"
            )
            @RequestParam(required = false) SortDirection taskStatusSortDirection,

            @Parameter(
                    description = "Optional sort direction for due date (ASC or DESC)",
                    example = "DESC"
            )
            @RequestParam(required = false) SortDirection dueDateSortDirection
    ) {
        return taskService.getTasks(new TaskListRequestDto(taskStatusFilter, taskStatusSortDirection, dueDateSortDirection));
    }


    @Operation(
            summary = "Get task by ID",
            description = "Returns a task by its ID if it belongs to the authenticated user"
    )
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(
            @Parameter(
                    description = "ID of the task to retrieve",
                    example = "2"
            )
            @PathVariable Integer id
    ) throws AccessDeniedException {
        return taskService.getTaskById(new TaskFindRequestDto(id));
    }

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
    @PostMapping
    public TaskResponseDto createTask(@RequestBody TaskCreateRequestDto taskCreateRequestDto) {
        return taskService.addTask(taskCreateRequestDto);
    }


    @Operation(
            summary = "Delete a task by ID",
            description = "Deletes a task by its ID. Only the task owner is allowed to delete it."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id) throws AccessDeniedException {
        taskService.deleteTaskById(new TaskFindRequestDto(id));
        return ResponseEntity.ok(Collections.singletonMap("ok", true));
    }

}
