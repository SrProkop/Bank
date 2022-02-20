package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<Account> getMyAccounts(@AuthenticationPrincipal User user) {
        return accountService.findByUserId(user.getId());
    }

    @GetMapping("/{index}")
    public Account getMyAccount(@AuthenticationPrincipal User user,
                                @PathVariable int index) throws NotFoundException {
       return accountService.findByIndexForUser(user, index);
    }

    @DeleteMapping("/{index}")
    public Response deleteMyAccount(@AuthenticationPrincipal User user,
                                    @PathVariable int index) throws NotFoundException {
        List<Account> accounts = accountService.findByUserId(user.getId());
        try {
            accountService.deleteById(accounts.get(index - 1).getId());
            return new Response("Account deleted");
        } catch (IndexOutOfBoundsException | NotFoundException e) {
            throw new NotFoundException("Account not found");
        }
    }

    @PostMapping
    public Account createAccount(@AuthenticationPrincipal User user,
                             @RequestParam String currencyCode) throws NotFoundException {
        Currency currency = currencyService.findByCode(currencyCode);
        return accountService.createAccount(user, currency);
    }
}
