package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.Account;

import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void removeAccountById(Long id);

    Set<Account> findAllByClientId(Long id);

}
