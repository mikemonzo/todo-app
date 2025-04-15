package com.example.todo.category.service;

import org.springframework.stereotype.Service;
import com.example.todo.category.model.CategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
}
