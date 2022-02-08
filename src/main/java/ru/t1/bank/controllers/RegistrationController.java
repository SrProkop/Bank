package ru.t1.bank.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.models.User;
import ru.t1.bank.service.UserService;

@RestController
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public User addUser(@RequestParam String fullName,
                        @RequestParam String dateOfBirth,
                        @RequestParam String username,
                        @RequestParam String password) throws IncorrectDataException {
        return userService.createUser(fullName, dateOfBirth, username, passwordEncoder.encode(password));
    }
}
