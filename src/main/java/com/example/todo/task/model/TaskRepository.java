package com.example.todo.task.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom query methods can be defined here if needed

}
