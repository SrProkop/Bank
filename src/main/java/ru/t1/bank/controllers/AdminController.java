package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.TransactionService;
import ru.t1.bank.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;

    public AdminController(AccountService accountService,
                             UserService userService,
                             CurrencyService currencyService,
                           TransactionService transactionService) {
        this.accountService = accountService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.transactionService = transactionService;
    }

    @GetMapping("/user")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable long id) throws NotFoundException {
        return userService.findById(id);
    }

    @DeleteMapping("/user/{id}")
    public Response deleteUser(@PathVariable long id) throws NotFoundException {
        userService.deleteById(id);
        return new Response("User deleted");
    }

    @GetMapping("/account")
    public List<Account> allAccount() {
        return accountService.findAll();
    }

    @GetMapping("/account/{id}")
    public Account findAccountById(@PathVariable long id) throws NotFoundException {
        return accountService.findById(id);
    }

    @DeleteMapping("account/{id}")
    public Response closeAccount(@PathVariable long id) throws NotFoundException {
        accountService.deleteById(id);
        return new Response("Account deleted");
    }

    @GetMapping("/transaction")
    public List<Transaction> allTransaction() {
        return transactionService.findAll();
    }

    @GetMapping("/transaction/{id}")
    public Transaction findTransactionById(@PathVariable long id) throws NotFoundException {
        return transactionService.findById(id);
    }

    @DeleteMapping("/transaction/{id}")
    public Response deleteTransaction(@PathVariable Long id) throws NotFoundException {
        transactionService.deleteById(id);
        return new Response("Account deleted");
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