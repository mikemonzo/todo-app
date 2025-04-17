package com.example.todo.category.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.todo.category.model.Category;
import com.example.todo.category.model.CategoryRepository;
import com.example.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final TaskService taskService;

    private final CategoryRepository categoryRepository;

    CategoryService(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by("title").ascending());
    }

    public void deleteById(Long id) {
        if (id != 1L) {
            Category oldCategory = categoryRepository.getReferenceById(id);
            Category mainCategory = categoryRepository.getReferenceById(1L);
            taskService.updateCategory(oldCategory, mainCategory);
            categoryRepository.deleteById(id);
        }
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
