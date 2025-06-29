package ru.todo.spring.javadaddy.todoApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.todo.spring.javadaddy.todoApp.controller.openApiConfig.TaskControllerOpenApiConfig;
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
@SecurityRequirement(name = "bearerAuth")
public class TaskController implements TaskControllerOpenApiConfig {

    private final TaskService taskService;

    @Autowired
    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public TaskListResponseDto getTasks(@RequestParam(required = false) TaskStatus taskStatusFilter,
                                        @RequestParam(required = false) SortDirection taskStatusSortDirection,
                                        @RequestParam(required = false) SortDirection dueDateSortDirection
    ) {
        return taskService.getTasks(new TaskListRequestDto(taskStatusFilter, taskStatusSortDirection, dueDateSortDirection));
    }


    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Integer id) throws AccessDeniedException {
        return taskService.getTaskById(new TaskFindRequestDto(id));
    }

    @PostMapping
    public TaskResponseDto createTask(@RequestBody TaskCreateRequestDto taskCreateRequestDto) {
        return taskService.addTask(taskCreateRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id) throws AccessDeniedException {
        taskService.deleteTaskById(new TaskFindRequestDto(id));
        return ResponseEntity.ok(Collections.singletonMap("ok", true));
    }

}
