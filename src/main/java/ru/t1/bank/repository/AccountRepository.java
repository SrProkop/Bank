package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    void removeAccountById(Long id);

}
