package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final CurrencyService currencyService;

    public AccountController(AccountService accountService,
                             UserService userService,
                             CurrencyService currencyService) {
        this.accountService = accountService;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @GetMapping("/all")
    public List<Account> allAccount() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findAccountById(@PathVariable long id) throws NotFoundException {
        return accountService.findById(id);
    }

    @PostMapping("/create")
    public Account createAccountForUser(@RequestParam long userId,
                             @RequestParam String currencyCode) throws NotFoundException {
        User user = userService.findById(userId);
        Currency currency = currencyService.findByCode(currencyCode);
        return accountService.createAccount(user, currency);
    }

    @PostMapping("/delete/{id}")
    public Response closeAccount(@PathVariable long id) throws NotFoundException {
        accountService.deleteById(id);
        return new Response("Account deleted");
    }
}
