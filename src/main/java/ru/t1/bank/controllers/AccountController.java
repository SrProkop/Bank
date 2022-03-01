package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.dto.AccountDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.mappers.AccountMapper;
import ru.t1.bank.mappers.UserMapper;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.CurrencyService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService,
                             CurrencyService currencyService,
                             AccountMapper accountMapper) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public Set<AccountDTO> getMyAccounts(@AuthenticationPrincipal User user) {
        return accountMapper.toAccountDTO(accountService.findByUserId(user.getId()));
    }

    @GetMapping("/{index}")
    public AccountDTO getMyAccount(@AuthenticationPrincipal User user,
                                @PathVariable int index) throws NotFoundException {
       return accountMapper.toAccountDTO(accountService.findByIndexForUser(user, index));
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
    public AccountDTO createAccount(@AuthenticationPrincipal User user,
                             @RequestParam String currencyCode) throws NotFoundException {
        Currency currency = currencyService.findByCode(currencyCode);
        return accountMapper.toAccountDTO(accountService.createAccount(user, currency));
    }
}
