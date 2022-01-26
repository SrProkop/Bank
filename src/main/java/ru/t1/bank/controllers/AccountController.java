package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.enums.Role;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;
import ru.t1.bank.service.UserService;
import ru.t1.bank.utils.DateConverter;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    CurrencyService currencyService;

    @GetMapping("/accounts")
    public List<Account> allAccount() {
        return accountService.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account findAccountById(@PathVariable long id) {
        return accountService.findById(id);
    }

    @PostMapping("/accounts/create")
    public String createAccountForUser(@RequestParam long userId,
                             @RequestParam String currencyCode) {
        User user = userService.findById(userId);
        Currency currency = currencyService.findByCode(currencyCode);
        accountService.createAccount(user, currency);
        return "account created";
    }

    @PostMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable long id) {
        if (accountService.accountIsPresent(id)) {
            accountService.deleteById(id);
            return "account delete";
        } else {
            return "account not found";
        }
    }

}
