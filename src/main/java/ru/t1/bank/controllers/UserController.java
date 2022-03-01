package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.dto.UserDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.mappers.UserMapper;
import ru.t1.bank.models.User;
import ru.t1.bank.service.UserService;

@RestController
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public UserDTO getUser(@AuthenticationPrincipal User user) throws NotFoundException {
        return userMapper.toUserDTO(userService.findById(user.getId()));
    }
}