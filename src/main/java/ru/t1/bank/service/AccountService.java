package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    public void createAccount(User user, Currency currency) {
        Account account = new Account();
        account.setMoney(BigDecimal.ZERO);
        account.setClient(user);
        account.setCurrency(currency);
        //Придумать способ генерации номера счёт получше
        account.setNumber(String.valueOf(1000_000_000_000l + findAll().size()));
        createAccount(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public void saveAll(List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

    public boolean accountIsPresent(long id) {
        return accountRepository.findById(id).isPresent();
    }


}
