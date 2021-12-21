package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
