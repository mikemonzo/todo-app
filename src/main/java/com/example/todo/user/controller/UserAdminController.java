package com.example.todo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.todo.user.dto.UserResponse;
import com.example.todo.user.model.UserRole;
import com.example.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/admin/user")
    public String listUsers(Model model) {
        model.addAttribute("userList",
                userService.findAll().stream().map(UserResponse::of).toList());
        return "admin/admin-users";
    }

    @GetMapping("/admin/user/{id}/promote")
    public String makeAdmin(@PathVariable Long id, Model model) {
        userService.changeRole(id, UserRole.ADMIN);
        return "redirect:/admin/user";
    }

}
