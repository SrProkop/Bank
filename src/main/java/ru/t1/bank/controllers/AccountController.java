package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.UserService;

import java.util.Set;

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

    @GetMapping
    public Set<Account> myAccount(@AuthenticationPrincipal User user) {
        return accountService.findByUserId(user.getId());
    }

    @PostMapping
    public Account createAccountForUser(@AuthenticationPrincipal User user,
                             @RequestParam String currencyCode) throws NotFoundException {
        Currency currency = currencyService.findByCode(currencyCode);
        return accountService.createAccount(user, currency);
    }

}
