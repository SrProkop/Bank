package ru.t1.bank.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.bank.dto.TransactionDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.service.AccountService;
import ru.t1.bank.service.TransactionService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public abstract class TransactionMapper {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Mappings({
            @Mapping(target = "accountFrom", source =
                    "accountFrom.number"),
            @Mapping(target = "accountTo", source =
                    "accountTo.number")
    })
    public abstract TransactionDTO toTransactionDTO(Transaction transaction) throws NotFoundException;

    @Mappings({
            @Mapping(target = "accountFrom", expression =
                    "java(accountService.findByNumber(transactionDTO.getAccountFrom()))"),
            @Mapping(target = "accountTo", expression =
                    "java(accountService.findByNumber(transactionDTO.getAccountTo()))"),
    })
    public abstract Transaction toTransaction(TransactionDTO transactionDTO) throws NotFoundException;

    public abstract Set<TransactionDTO> toTransactionDTO(Collection<Transaction> transaction);

    public abstract Set<Transaction> toTransaction(Collection<TransactionDTO> transactionDTO);

}
