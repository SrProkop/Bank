package ru.t1.bank.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Status;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) throws NotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        return account.get();
    }

    public Set<Account> findByUserId(Long id) {
        return accountRepository.findAllByClientId(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account createAccount(User user, Currency currency) {
        Account account = new Account();
        account.setMoney(BigDecimal.ZERO);
        account.setClient(user);
        account.setCurrency(currency);
        account.setOpen(true);
        //Придумать способ генерации номера счёт получше
        account.setNumber(String.valueOf(1000_000_000_000l + findAll().size()));
        return accountRepository.save(account);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    public boolean accountIsPresent(long id) {
        return accountRepository.findById(id).isPresent();
    }

}
