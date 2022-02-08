package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
