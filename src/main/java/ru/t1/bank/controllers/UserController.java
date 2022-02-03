package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.User;
import ru.t1.bank.service.UserService;
import ru.t1.bank.Response;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) throws NotFoundException {
        return userService.findById(id);
    }

    @PostMapping("/create")
    public User createUser(@RequestParam String name,
                             @RequestParam String dateOfBirth) throws IncorrectDataException {
        return userService.createUser(name, dateOfBirth);
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteUser(@PathVariable long id) throws NotFoundException {
        userService.deleteById(id);
        return new Response("User deleted");
    }

}
