package ru.t1.bank.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.dto.TransactionDTO;
import ru.t1.bank.exceptions.InsufficientFundsException;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.mappers.TransactionMapper;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping  // + .../{index}/...???
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;
    private TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService,
                                 AccountService accountService,
                                 TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/account/{index}/transaction")
    public Set<TransactionDTO> getTransactionsForAccounts(@AuthenticationPrincipal User user,
                                                          @PathVariable int index,
                                                          @RequestParam(required = false) Integer page) throws NotFoundException {
        Account account = accountService.findByIndexForUser(user, index);
        return transactionMapper.toTransactionDTO(transactionService.findTransactionForAccount(account, page));
    }

    @PostMapping("/account/{index}/transfer")
    public TransactionDTO createTransaction(@AuthenticationPrincipal User user,
                                      @PathVariable int index,
                                      @RequestParam String number,
                                      @RequestParam BigDecimal sum) throws NotFoundException, InsufficientFundsException {
        return transactionMapper.toTransactionDTO(transactionService.transfer(user, index, number, sum));
    }

}
