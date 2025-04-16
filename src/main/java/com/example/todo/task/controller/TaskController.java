package com.example.todo.task.controller;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.example.todo.category.model.Category;
import com.example.todo.category.service.CategoryService;
import com.example.todo.task.dto.CreateTaskRequest;
import com.example.todo.task.dto.EditTaskRequest;
import com.example.todo.task.model.Task;
import com.example.todo.task.service.TaskService;
import com.example.todo.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping({"/", "/list", "/task"})
    public String taskList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("taskList", taskService.findAllByUser(user));
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    @GetMapping(value = {"/", "/list", "/task"}, params = "emptyListError")
    public String createTask(Model model) {
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    @PostMapping("/task/submit")
    public String taskSubmit(@Valid @ModelAttribute("newTask") CreateTaskRequest request,
            BindingResult bindingResult, @AuthenticationPrincipal User author, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("taskList", taskService.findAllByUser(author));
            return "task-list";
        }

        taskService.createTask(request, author);
        return "redirect:/";
    }

    @GetMapping("/task/{id}")
    public String viewOrEditTask(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        EditTaskRequest editTaskRequest = EditTaskRequest.of(task);
        model.addAttribute("task", editTaskRequest);
        return "show-task";
    }

    @PostMapping("/task/edit/submit")
    public String taskEditSubmit(@Valid @ModelAttribute("task") EditTaskRequest request,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "show-task";
        }

        taskService.editTask(request);
        return "redirect:/";

    }

    @GetMapping("/task/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleComplete(id);
        return "redirect:/";
    }

    @PostMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);

        return "redirect:/";
    }


}

