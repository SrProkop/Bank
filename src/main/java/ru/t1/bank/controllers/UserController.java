package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.enums.Role;
import ru.t1.bank.models.User;
import ru.t1.bank.service.UserService;
import ru.t1.bank.utils.DateConverter;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profiles")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/profiles/{id}")
    public User findUserById(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping("/profiles/create")
    public String createUser(@RequestParam String name,
                             @RequestParam String dateOfBirth) {
        if (userService.createUser(name, dateOfBirth) != null) {
            return "user created";
        }
        return "user not created";
    }

    @PostMapping("/profiles/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        if (userService.userIsPresent(id)) {
            userService.deleteById(id);
            return "user delete";
        } else {
            return "user not found";
        }
    }

}
