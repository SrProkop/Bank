package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;

    @GetMapping("/transactions")
    public List<Transaction> allTransaction() {
        return transactionService.findAll();
    }

    @PostMapping("/transaction/{id}")
    public Transaction findTransactionById(@PathVariable long id) {
        return transactionService.findById(id);
    }

    @PostMapping("/transaction/deposit")
    public String deposit(@RequestParam long userId,
                          @RequestParam long sum) {
        if (!accountService.accountIsPresent(userId)) {
            return "account not found";
        }
        Account account = accountService.findById(userId);
        transactionService.deposit(account, sum);
        accountService.createAccount(account);
        return "transaction created";
    }

    @PostMapping("/transaction/withdraw")
    public String withdraw(@RequestParam long userId,
                          @RequestParam long sum) {
        if (!accountService.accountIsPresent(userId)) {
            return "account not found";
        }
        Account account = accountService.findById(userId);
        if (transactionService.withdraw(account, sum) != null) {
            accountService.createAccount(account);
            return "transaction created";
        }
        return "transaction not created";
    }


}
