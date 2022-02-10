package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

@RestController
@RequestMapping("/account/transaction")
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

}
