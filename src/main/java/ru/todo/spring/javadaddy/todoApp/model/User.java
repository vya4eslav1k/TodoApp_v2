package ru.todo.spring.javadaddy.todoApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, length = 50)
    private String login;
    @Column(unique = true, nullable = false, length = 50)
    private String password;
}
