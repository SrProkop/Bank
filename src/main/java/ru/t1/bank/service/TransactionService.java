package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Type;
import ru.t1.bank.models.Account;
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

    public List<Transaction> findByType(Type type) {
        return transactionRepository.findByType(type);
    }

    public void deposit(Account account, long sum) {
        Transaction transaction = new Transaction();
        transaction.setSumTo(BigDecimal.valueOf(sum));
        transaction.setType(Type.Deposit);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountTo(account);
        account.setMoney(account.getMoney().add(BigDecimal.valueOf(sum)));
        transactionRepository.save(transaction);
        account.getTransactionsTo().add(transaction);
    }

    public Transaction withdraw(Account account, long sum) {
        Transaction transaction = new Transaction();
        if (account.getMoney().compareTo(BigDecimal.valueOf(sum)) == -1) {
            return null;
        }
        transaction.setSumTo(BigDecimal.valueOf(sum));
        transaction.setType(Type.Withdraw);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountFrom(account);
        account.setMoney(account.getMoney().subtract(BigDecimal.valueOf(sum)));
        transactionRepository.save(transaction);
        account.getTransactionsFrom().add(transaction);
        return transaction;
    }

}
