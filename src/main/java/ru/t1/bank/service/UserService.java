package ru.t1.bank.service;

import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Role;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.UserRepository;
import ru.t1.bank.utils.DateConverter;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User createUser(String fullName, String date) throws IncorrectDataException {
        User user = new User();
        user.setFullName(fullName);
        user.setDateOfBirth(DateConverter.converterLocalDate(date));
        user.getRoles().add(Role.Client);
        return userRepository.save(user);
    }

    public void deleteById(Long id) throws NotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

}