package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.TransactionRepository;
import ru.t1.bank.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findTransactionWhereSumWhere(Long sum) {
        return transactionRepository.findTransactionWhereSumMore(new BigDecimal(sum));
    }

    public List<Transaction> findTransactionByDateBefore(LocalDateTime dateTime) {
        return transactionRepository.findByDateBefore(dateTime);
    }

}
