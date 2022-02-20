package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.User;
import ru.t1.bank.service.UserService;

@RestController
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUser(@AuthenticationPrincipal User user) throws NotFoundException {
        return userService.findById(user.getId());
    }
}