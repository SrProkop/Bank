package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<Transaction> allTransaction() {
        return transactionService.findAll();
    }

    @PostMapping("/{id}")
    public Transaction findTransactionById(@PathVariable long id) throws NotFoundException {
        return transactionService.findById(id);
    }

    @PostMapping("/deposit")
    public Response deposit(@RequestParam long accountId,
                            @RequestParam long sum) throws NotFoundException {
        Account account = accountService.findById(accountId);
        transactionService.deposit(account, sum);
        accountService.createAccount(account);
        return new Response("Deposit passed");
    }

    @PostMapping("/withdraw")
    public Response withdraw(@RequestParam long accountId,
                          @RequestParam long sum) throws NotFoundException, InsufficientFundsException {
        Account account = accountService.findById(accountId);
        transactionService.withdraw(account, sum);
        accountService.createAccount(account);
        return new Response("Withdraw passed");
    }

}
