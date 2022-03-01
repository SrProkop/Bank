package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.bank.enums.Type;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;
import ru.t1.bank.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public List<Transaction> findAll(Integer numberPage) {
        if (numberPage == null) {
            numberPage = 1;
        }
        Pageable pageable = PageRequest.of(numberPage - 1, 20, Sort.by("id").descending());
        return transactionRepository.findAll(pageable).toList();
    }

    public Transaction findById(Long id) throws NotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        return transaction.get();
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findTransactionForAccount(Account account, Integer numberPage) {
        if (numberPage == null) {
            numberPage = 1;
        }
        Pageable pageable = PageRequest.of(numberPage - 1, 10, Sort.by("id").descending());
        return transactionRepository.findAllByAccountFromOrAccountTo(account, account, pageable);
    }

    @Transactional
    public void deposit(Long accountId, long sum) throws InsufficientFundsException {
        Account account = accountRepository.getById(accountId);
        Transaction transaction = new Transaction();
        transaction.setSumTo(BigDecimal.valueOf(sum));
        transaction.setType(Type.Deposit);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountTo(account);
        account.setMoney(account.getMoney().add(BigDecimal.valueOf(sum)));
        account.getTransactionsTo().add(transaction);
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Long accountId, long sum) throws InsufficientFundsException {
        Account account = accountRepository.findById(accountId).get();
        Transaction transaction = new Transaction();
        transaction.setSumTo(BigDecimal.valueOf(sum));
        transaction.setType(Type.Withdraw);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccountFrom(account);
        account.setMoney(account.getMoney().subtract(BigDecimal.valueOf(sum)));
        account.getTransactionsFrom().add(transaction);
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Transactional
    public Transaction transfer(User userFrom,
                                Integer indexAccountForUser,
                                String numberAccountTo,
                                BigDecimal sum) throws InsufficientFundsException, NotFoundException {
        Account accountFrom = accountService.findByIndexForUser(userFrom, indexAccountForUser);
        Account accountTo = accountService.findByNumber(numberAccountTo);
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
