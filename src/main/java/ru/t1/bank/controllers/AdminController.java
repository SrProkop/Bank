package ru.t1.bank.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.TransactionService;
import ru.t1.bank.service.UserService;

import java.math.BigDecimal;
import java.nio.file.Path;
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

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) throws NotFoundException {
        return userService.findById(id);
    }

    @DeleteMapping("/user/{id}")
    public Response deleteUser(@PathVariable long id) throws NotFoundException {
        userService.deleteById(id);
        return new Response("User deleted");
    }

    @GetMapping("account/page{number}")
    public List<Account> getAccounts(@PathVariable int number,
                                     @RequestParam(required = false) String currencyCode,
                                     @RequestParam(required = false) BigDecimal fromSum,
                                     @RequestParam(required = false) BigDecimal upToSum) throws NotFoundException {
        Currency currency;
        if (currencyCode != null) {
            currency = currencyService.findByCode(currencyCode);
        } else {
            currency = null;
        }
        return accountService.findAll(number, currency, fromSum, upToSum);
    }

    @GetMapping("/account/{number}")
    public Account getAccountByNumber(@PathVariable String number) throws NotFoundException {
        return accountService.findByNumber(number);
    }

    @DeleteMapping("account/{number}")
    public Response deleteAccount(@PathVariable String number) throws NotFoundException {
        accountService.deleteByNumber(number);
        return new Response("Account deleted");
    }

    @GetMapping("/account/{numberAccount}/transaction/page{numberPage}")
    public List<Transaction> getTransactionsForAccountByNumber(@PathVariable String numberAccount,
                                                       @PathVariable int numberPage) throws NotFoundException {
        Account account = accountService.findByNumber(numberAccount);
        return transactionService.findTransactionForAccount(account, numberPage);
    }

    @GetMapping("/transaction/page{number}")
    public Page<Transaction> getTransactions(@PathVariable int number) {
        return transactionService.findAll(number);
    }

    @GetMapping("/transaction/{id}")
    public Transaction getTransactionById(@PathVariable long id) throws NotFoundException {
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