package com.example.todo.task.service;

import org.springframework.stereotype.Service;
import com.example.todo.task.model.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

}
