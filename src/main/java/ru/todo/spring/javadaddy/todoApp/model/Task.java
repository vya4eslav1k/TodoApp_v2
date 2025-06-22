package ru.todo.spring.javadaddy.todoApp.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.todo.spring.javadaddy.todoApp.enums.TaskStatus;

import java.time.LocalDate;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "title", length = 50)
    private String title;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "dueDate")
    private LocalDate dueDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
