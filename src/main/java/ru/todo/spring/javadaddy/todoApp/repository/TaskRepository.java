package ru.todo.spring.javadaddy.todoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.todo.spring.javadaddy.todoApp.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
