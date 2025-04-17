package com.example.todo.task.model;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todo.category.model.Category;
import com.example.todo.user.model.User;


public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom query methods can be defined here if needed
    List<Task> findByAuthor(User author, Sort sort);

    List<Task> findByCategory(Category category);
}
