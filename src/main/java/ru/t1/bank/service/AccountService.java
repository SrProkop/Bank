package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.bank.models.Account;
import ru.t1.bank.repository.AccountRepository;

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

}
