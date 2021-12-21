package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
