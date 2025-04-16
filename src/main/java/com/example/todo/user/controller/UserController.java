package com.example.todo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.todo.user.dto.CreateUserRequest;
import com.example.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/register")
    public String howRegisterForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "register";
    }

    @PostMapping("/auth/register/submit")
    public String processRegisterForm(@ModelAttribute("user") CreateUserRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(request);

        return "redirect:/login";
    }

}
