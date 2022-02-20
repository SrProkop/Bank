package ru.t1.bank.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.t1.bank.models.Account;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    List<Account> findAllByClientId(Long id);

    Optional<Account> findByNumber(String number);

}
