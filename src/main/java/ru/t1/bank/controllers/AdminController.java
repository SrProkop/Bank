package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.dto.AccountDTO;
import ru.t1.bank.dto.TransactionDTO;
import ru.t1.bank.dto.UserDTO;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.mappers.AccountMapper;
import ru.t1.bank.mappers.CurrencyMapper;
import ru.t1.bank.mappers.TransactionMapper;
import ru.t1.bank.mappers.UserMapper;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.TransactionService;
import ru.t1.bank.service.UserService;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;
    private final UserService userService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final CurrencyMapper currencyMapper;

    public AdminController(AccountService accountService,
                           UserService userService,
                           CurrencyService currencyService,
                           TransactionService transactionService, TransactionMapper transactionMapper, UserMapper userMapper, AccountMapper accountMapper, CurrencyMapper currencyMapper) {
        this.accountService = accountService;
        this.userService = userService;
        this.currencyService = currencyService;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.userMapper = userMapper;
        this.accountMapper = accountMapper;
        this.currencyMapper = currencyMapper;
    }

    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable long id) throws NotFoundException {
        return userMapper.toUserDTO(userService.findById(id));
    }

    @DeleteMapping("/user/{id}")
    public Response deleteUser(@PathVariable long id) throws NotFoundException {
        userService.deleteById(id);
        return new Response("User deleted");
    }

    @GetMapping("account")
    public Set<AccountDTO> getAccounts(@RequestParam(required = false) String currencyCode,
                                       @RequestParam(required = false) BigDecimal fromSum,
                                       @RequestParam(required = false) BigDecimal upToSum,
                                       @RequestParam(required = false) Integer page) throws NotFoundException {
        Currency currency;
        if (currencyCode != null) {
            currency = currencyService.findByCode(currencyCode);
        } else {
            currency = null;
        }
        return accountMapper.toAccountDTO(accountService.findAll(page, currency, fromSum, upToSum));
    }

    @GetMapping("/account/{number}")
    public AccountDTO getAccountByNumber(@PathVariable String number) throws NotFoundException {
        return accountMapper.toAccountDTO(accountService.findByNumber(number));
    }

    @DeleteMapping("account/{number}")
    public Response deleteAccount(@PathVariable String number) throws NotFoundException {
        accountService.deleteByNumber(number);
        return new Response("Account deleted");
    }

    @GetMapping("/account/{numberAccount}/transaction")
    public Set<TransactionDTO> getTransactionsForAccountByNumber(@PathVariable String numberAccount,
                                                                     @RequestParam(required = false) Integer page) throws NotFoundException {
        Account account = accountService.findByNumber(numberAccount);
        return transactionMapper.toTransactionDTO(transactionService.findTransactionForAccount(account, page));
    }

    @GetMapping("/transaction")
    public Set<TransactionDTO> getTransactions(@RequestParam(required = false) Integer page) {
        return transactionMapper.toTransactionDTO(transactionService.findAll(page));
    }

    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransactionById(@PathVariable long id) throws NotFoundException {
        return transactionMapper.toTransactionDTO(transactionService.findById(id));
    }

    @DeleteMapping("/transaction/{id}")
    public Response deleteTransaction(@PathVariable Long id) throws NotFoundException {
        transactionService.deleteById(id);
        return new Response("Account deleted");
    }

    @PostMapping("/deposit")
    public Response deposit(@RequestParam long accountId,
                            @RequestParam long sum) throws NotFoundException, InsufficientFundsException {
        transactionService.deposit(accountId, sum);
        return new Response("Deposit passed");
    }

    @PostMapping("/withdraw")
    public Response withdraw(@RequestParam long accountId,
                             @RequestParam long sum) throws NotFoundException, InsufficientFundsException {
        transactionService.withdraw(accountId, sum);
        return new Response("Withdraw passed");
    }
}