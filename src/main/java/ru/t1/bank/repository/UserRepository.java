package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.enums.Role;
import ru.t1.bank.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRoles(Role role);

}
