package com.example.todo.shared.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.todo.task.exception.EmptyTaskListException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyTaskListException.class)
    public String emptyTaskList(EmptyTaskListException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("emptyListError", true);
        return "redirect:/";
    }
}
