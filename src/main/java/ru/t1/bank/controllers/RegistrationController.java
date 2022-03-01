package ru.t1.bank.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.bank.dto.UserDTO;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.mappers.CurrencyMapper;
import ru.t1.bank.mappers.UserMapper;
import ru.t1.bank.service.UserService;

@RestController
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;


    public RegistrationController(UserService userService,
                                  PasswordEncoder passwordEncoder,
                                  UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;

    }

    @PostMapping("/registration")
    public UserDTO createUser(@RequestParam String fullName,
                              @RequestParam String dateOfBirth,
                              @RequestParam String username,
                              @RequestParam String password) throws IncorrectDataException {
        return userMapper.toUserDTO(userService.createUser(fullName, dateOfBirth, username, passwordEncoder.encode(password)));
    }
}
