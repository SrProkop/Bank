package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Role;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;
import ru.t1.bank.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRoles(role);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
