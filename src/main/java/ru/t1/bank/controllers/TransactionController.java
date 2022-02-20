package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping  // + .../{index}/...???
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/account/{index}/transaction/page{number}")
    public List<Transaction> getTransactionsForAccounts(@AuthenticationPrincipal User user,
                                                        @PathVariable int index,
                                                        @PathVariable int number) throws NotFoundException {
        Account account = accountService.findByIndexForUser(user, index);
        return transactionService.findTransactionForAccount(account, number);
    }

    @PostMapping("/account/{index}/transfer")
    public Transaction createTransaction(@AuthenticationPrincipal User user,
                                      @PathVariable int index,
                                      @RequestParam String number,
                                      @RequestParam BigDecimal sum) throws NotFoundException, InsufficientFundsException {
        Account accountFrom = accountService.findByIndexForUser(user, index);
        Account accountTo = accountService.findByNumber(number);
        return transactionService.transfer(accountTo, accountFrom, sum);
    }

}
