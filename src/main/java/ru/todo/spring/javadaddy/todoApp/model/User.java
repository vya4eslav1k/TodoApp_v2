package ru.todo.spring.javadaddy.todoApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "login", unique = true, nullable = false, length = 50)
    private String login;
    @Column(name = "password", nullable = false, length = 300)
    private String password;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "bio", length = 100)
    private String bio;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
