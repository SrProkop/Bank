package ru.t1.bank.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.t1.bank.exceptions.IncorrectDataException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Role;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.RoleRepository;
import ru.t1.bank.repository.UserRepository;
import ru.t1.bank.utils.DateConverter;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public User createUser(String fullName,
                           String date,
                           String username,
                           String password) throws IncorrectDataException {
        User user = new User();
        user.setDateOfBirth(DateConverter.converterLocalDate(date));
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);
        user.getRoles().add(new Role(1l, "ROLE_CLIENT"));
        return userRepository.save(user);
    }

    public void deleteById(Long id) throws NotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}