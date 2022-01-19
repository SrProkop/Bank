package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.enums.Type;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;
import ru.t1.bank.repository.CurrencyRepository;
import ru.t1.bank.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    @GetMapping("/profiles")
    public List<User> allProfiles() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/profiles/{id}")
    public User profile(@PathVariable long id) {
        User user = userRepository.getById(id);
        return user;
    }

    @PostMapping("/profiles/{id}/create-account")
    public String createAccountForUser(@PathVariable long id) {
        User user = userRepository.getById(id);
        Account account = new Account();
        account.setNumber("100000" + accountRepository.findAll().size());
        account.setClient(user);
        account.setMoney(new BigDecimal(1000l));
        account.setCurrency(currencyRepository.getById(1l));
        user.getAccounts().add(account);
        userRepository.save(user);
        return "account created";
    }

}
