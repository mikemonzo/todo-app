package com.example.todo.task.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.todo.category.model.Category;
import com.example.todo.category.model.CategoryRepository;
import com.example.todo.tag.service.TagService;
import com.example.todo.task.dto.CreateTaskRequest;
import com.example.todo.task.dto.EditTaskRequest;
import com.example.todo.task.exception.EmptyTaskListException;
import com.example.todo.task.exception.TaskNotfoundException;
import com.example.todo.task.model.Task;
import com.example.todo.task.model.TaskRepository;
import com.example.todo.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagService tagService;

    /*
     * public List<Task> findAll() { List<Task> tasks = taskRepository.findAll(); if
     * (tasks.isEmpty()) { throw new EmptyTaskListException(); } return tasks; }
     */

    private List<Task> findAll(User user) {
        List<Task> tasks = null;

        if (user != null)
            tasks = taskRepository.findByAuthor(user, Sort.by("createdAt").ascending());
        else
            tasks = taskRepository.findAll(Sort.by("createdAt").ascending());

        if (tasks.isEmpty()) {
            throw new EmptyTaskListException();
        }

        return tasks;
    }

    public List<Task> findAllByUser(User user) {
        return findAll(user);
    }

    public List<Task> findAllByAdmin() {
        return findAll(null);
    }

    public Task createTask(CreateTaskRequest request, User author) {
        return createOrEditTask(request, author);
    }

    public Task editTask(EditTaskRequest request) {
        return createOrEditTask(request, null);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotfoundException(id));
    }

    private Task createOrEditTask(CreateTaskRequest request, User author) {
        Task task = Task.builder().title(request.getTitle()).description(request.getDescription())
                .build();

        if (request.getCategoryId() == null || request.getCategoryId() == -1L)
            request.setCategoryId(1L);

        Category category = categoryRepository.getReferenceById(request.getCategoryId());

        if (category == null)
            category = categoryRepository.getReferenceById(1L);

        task.setCategory(category);

        List<String> textTags =
                Arrays.stream(request.getTags().split(",")).map(String::trim).toList();
        task.getTags().addAll(tagService.saveOrGet(textTags));

        if (request instanceof EditTaskRequest editReq) {
            Task oldTask = findById(editReq.getId());
            task.setId(oldTask.getId());
            task.setCreatedAt(oldTask.getCreatedAt());
            task.setAuthor(oldTask.getAuthor());
            task.setCompleted(editReq.isCompleted());
        } else {
            task.setAuthor(author);
        }

        return taskRepository.save(task);
    }

    public Task toggleComplete(Long id) {
        Task task = findById(id);
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
