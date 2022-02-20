package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Type;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;
import ru.t1.bank.repository.TransactionRepository;
import ru.t1.bank.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Page<Transaction> findAll(int numberPage) {
        Pageable pageable = PageRequest.of(numberPage - 1, 20, Sort.by("id").descending());
        return transactionRepository.findAll(pageable);
    }

    public Transaction findById(Long id) throws NotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        return transaction.get();
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findTransactionForAccount(Account account, int numberPage) {
        Pageable pageable = PageRequest.of(numberPage - 1, 10, Sort.by("id").descending());
        return transactionRepository.findAllByAccountFromOrAccountTo(account, account, pageable);
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

    public Transaction withdraw(Account account, long sum) throws InsufficientFundsException {
        Transaction transaction = new Transaction();
        if (account.getMoney().compareTo(BigDecimal.valueOf(sum)) == -1) {
            throw new InsufficientFundsException("Insufficient fund on account");
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

    public Transaction transfer(Account accountTo,
                                Account accountFrom,
                                BigDecimal sum) throws InsufficientFundsException {
        if (sum.compareTo(accountFrom.getMoney()) > 0) {
            throw new InsufficientFundsException("Insufficient fund on account");
        }
        Transaction transaction = new Transaction();
        transaction.setAccountTo(accountTo);
        transaction.setAccountFrom(accountFrom);
        transaction.setType(Type.Transfer);
        transaction.setSumTo(sum);
        transaction.setSumFrom(sum);
        accountFrom.setMoney(accountFrom.getMoney().subtract(sum));
        accountTo.setMoney(accountTo.getMoney().add(sum));
        accountRepository.save(accountTo);
        accountRepository.save(accountFrom);
        return transactionRepository.save(transaction);
    }

}
