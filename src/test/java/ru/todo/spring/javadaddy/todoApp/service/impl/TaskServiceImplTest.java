package ru.todo.spring.javadaddy.todoApp.service.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskCreateRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskFindRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.request.TaskListRequestDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskListResponseDto;
import ru.todo.spring.javadaddy.todoApp.dto.response.TaskResponseDto;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;
import ru.todo.spring.javadaddy.todoApp.model.Task;
import ru.todo.spring.javadaddy.todoApp.model.User;
import ru.todo.spring.javadaddy.todoApp.repository.TaskRepository;
import ru.todo.spring.javadaddy.todoApp.security.UserDetails;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserDetails userDetails;

    private final String TEST_LOGIN = "testUser";

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void getTasks_withFilterAndSort_returnsFilteredTasks() {
        Task task1 = Task.builder().id(1).status(TaskStatus.TODO).dueDate(LocalDate.now()).build();
        Task task2 = Task.builder().id(2).status(TaskStatus.DONE).dueDate(LocalDate.now().plusDays(1)).build();

        when(authentication.getName()).thenReturn(TEST_LOGIN);
        when(taskRepository.findAllByUser_Login(TEST_LOGIN)).thenReturn(List.of(task1, task2));

        TaskListRequestDto request = new TaskListRequestDto(
                TaskStatus.TODO,
                null,
                null
        );

        TaskListResponseDto response = taskService.getTasks(request);

        assertEquals(1, response.getTasks().size());
        assertEquals(task1.getId(), response.getTasks().getFirst().getId());
    }

    @Test
    void getTaskById_userHasAccess_returnsTask() throws AccessDeniedException {
        User user = new User();
        user.setId(1);

        Task task = Task.builder().id(1).user(user).build();

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUser()).thenReturn(user);

        TaskResponseDto response = taskService.getTaskById(new TaskFindRequestDto(1));

        assertEquals(task.getId(), response.getId());
    }

    @Test
    void getTaskById_userNoAccess_throwsException() {
        User taskOwner = new User();
        taskOwner.setId(1);
        Task task = Task.builder().id(1).user(taskOwner).build();

        User currentUser = new User();
        currentUser.setId(2);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUser()).thenReturn(currentUser);

        assertThrows(AccessDeniedException.class,
                () -> taskService.getTaskById(new TaskFindRequestDto(1)));
    }

    @Test
    void addTask_savesAndReturnsTask() {
        User user = new User();
        user.setId(1);

        TaskCreateRequestDto dto = new TaskCreateRequestDto();
        dto.setTitle("Test");
        dto.setDescription("Desc");
        dto.setDueDate(LocalDate.now());
        dto.setStatus(TaskStatus.TODO);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUser()).thenReturn(user);

        Task savedTask = Task.builder()
                .id(1)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .status(dto.getStatus())
                .user(user)
                .build();

        when(taskRepository.save(any())).thenReturn(savedTask);

        TaskResponseDto result = taskService.addTask(dto);

        assertEquals(savedTask.getId(), result.getId());
    }

    @Test
    void deleteTaskById_userHasAccess_deletesTask() throws AccessDeniedException {
        User user = new User();
        user.setId(1);
        Task task = Task.builder().id(1).user(user).build();

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUser()).thenReturn(user);

        taskService.deleteTaskById(new TaskFindRequestDto(1));

        verify(taskRepository).deleteById(1);
    }

    @Test
    void deleteTaskById_userNoAccess_throwsException() {
        User taskOwner = new User();
        taskOwner.setId(1);
        Task task = Task.builder().id(1).user(taskOwner).build();

        User currentUser = new User();
        currentUser.setId(2);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUser()).thenReturn(currentUser);

        assertThrows(AccessDeniedException.class,
                () -> taskService.deleteTaskById(new TaskFindRequestDto(1)));
    }
}
