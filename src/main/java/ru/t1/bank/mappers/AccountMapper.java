package ru.t1.bank.mappers;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.t1.bank.dto.AccountDTO;
import ru.t1.bank.dto.CurrencyDTO;
import ru.t1.bank.dto.TransactionDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.service.AccountService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    @Autowired
    CurrencyMapper currencyMapper;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AccountService accountService;

    public AccountDTO toAccountDTO(Account account) {
        System.out.println("_______________");
        System.out.println(account.getCurrency().toString());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setNumber(account.getNumber());
        accountDTO.setClient(userMapper.toUserDTO(account.getClient()));
        accountDTO.setCurrency(currencyMapper.toCurrencyDTO(account.getCurrency()));
        accountDTO.setMoney(account.getMoney());
        Set<TransactionDTO> transactionDTOS = new HashSet<>();
        transactionDTOS.addAll(transactionMapper.toTransactionDTO(account.getTransactionsFrom()));
        transactionDTOS.addAll(transactionMapper.toTransactionDTO(account.getTransactionsTo()));
        accountDTO.setTransactions(transactionDTOS);
        System.out.println(accountDTO.getCurrency().toString());
        return accountDTO;
    }

    public Account toAccount(AccountDTO accountDTO) throws NotFoundException {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setMoney(accountDTO.getMoney());
        account.setOpen(accountDTO.isOpen());
        account.setNumber(accountDTO.getNumber());
        account.setCurrency(currencyMapper.toCurrency(accountDTO.getCurrency()));
        account.setClient(userMapper.toUser(accountDTO.getClient()));
        if (accountDTO.getId() != null) {
            Account accountOld = accountService.findById(accountDTO.getId());
            account.setDateOpen(accountOld.getDateOpen());
            account.setLastModifiedDate(accountOld.getLastModifiedDate());
            account.setLastModifiedBy(accountOld.getLastModifiedBy());
        }
        for (TransactionDTO transactionDTO : accountDTO.getTransactions()) {
            if (transactionDTO.getAccountFrom().equals(accountDTO.getNumber())) {
                account.getTransactionsFrom().
                        add(transactionMapper.toTransaction(transactionDTO));
            } else {
                account.getTransactionsTo().
                        add(transactionMapper.toTransaction(transactionDTO));
            }
        }
        return account;
    }

    public abstract Set<AccountDTO> toAccountDTO(Collection<Account> accounts);

    public abstract Set<Account> toAccount(Collection<AccountDTO> accountDTOS);


}
