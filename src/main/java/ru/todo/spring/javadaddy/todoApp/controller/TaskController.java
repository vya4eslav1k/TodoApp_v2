package ru.todo.spring.javadaddy.todoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.todo.spring.javadaddy.todoApp.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    private TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

}
