package ru.todo.spring.javadaddy.todoApp.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskCreateRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskFindRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskListRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskListResponseDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskResponseDto;
import ru.todo.spring.javadaddy.todoApp.model.Task;
import ru.todo.spring.javadaddy.todoApp.model.User;
import ru.todo.spring.javadaddy.todoApp.repository.TaskRepository;
import ru.todo.spring.javadaddy.todoApp.security.UserDetails;
import ru.todo.spring.javadaddy.todoApp.service.TaskService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Stream;


@Service
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskListResponseDto getTasks(TaskListRequestDto taskDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserLogin = authentication.getName();
        Stream<Task> taskStream = taskRepository.findAllByUser_Login(currentUserLogin).stream();
        if (taskDto.getTaskStatusFilter() != null) {
            taskStream = taskStream.filter(t -> t.getStatus().equals(taskDto.getTaskStatusFilter()));
        }
        if (taskDto.getTaskStatusSortDirection() != null) {
            taskStream = taskStream.sorted((t1, t2) -> Integer.compare(t1.getStatus().getSortPriority(), t2.getStatus().getSortPriority()));
        }
        if (taskDto.getDueDateSortDirection() != null) {
            taskStream = taskStream.sorted((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
        }
        List<TaskResponseDto> tasks = taskStream.map(TaskResponseDto::new).toList();
        return new TaskListResponseDto(tasks);
    }

    @Override
    public TaskResponseDto getTaskById(TaskFindRequestDto taskDto) throws AccessDeniedException {
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task with id" + taskDto.getId() + " not found"));
        if (!userHasAccessToTask(task)) {
            throw new AccessDeniedException("User has no rights to access this entity");
        }
        return new TaskResponseDto(task);
    }

    @Override
    public TaskResponseDto addTask(TaskCreateRequestDto taskDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .dueDate(taskDto.getDueDate())
                .status(taskDto.getStatus())
                .user(user)
                .build();
        task = taskRepository.save(task);
        return new TaskResponseDto(task);
    }

    @Override
    public void deleteTaskById(TaskFindRequestDto taskDto) throws AccessDeniedException {
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task with id" + taskDto.getId() + " not found"));
        if (!userHasAccessToTask(task)) {
            throw new AccessDeniedException("User has no rights to access this entity");
        }
        taskRepository.deleteById(taskDto.getId());
    }

    private boolean userHasAccessToTask(Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currentUser = userDetails.getUser();
        return currentUser.getId().equals(task.getUser().getId());
    }
}
