package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.t1.bank.enums.Type;
import ru.t1.bank.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.sumTo > :sum")
    List<Transaction> findTransactionWhereSumMore(@Param("sum") BigDecimal sum);

    List<Transaction> findByDateBefore(LocalDateTime date);

    List<Transaction> findByType(Type type);

}
