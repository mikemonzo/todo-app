package com.example.todo.task.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotfoundException extends EntityNotFoundException {

    public TaskNotfoundException(String message) {
        super(message);
    }

    public TaskNotfoundException(Long id) {
        super("Task with id: %d not found".formatted(id));
    }

}
