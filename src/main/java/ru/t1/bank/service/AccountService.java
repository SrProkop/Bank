package ru.t1.bank.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.t1.bank.enums.Status;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.User;
import ru.t1.bank.repository.AccountRepository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EntityManager em;

    public AccountService(AccountRepository accountRepository, EntityManager em) {
        this.accountRepository = accountRepository;
        this.em = em;
    }

    public List<Account> findAll(int numberPage, Currency currency, BigDecimal fromSum, BigDecimal upToSum) throws NotFoundException {
        Pageable pageable = PageRequest.of(numberPage - 1, 5, Sort.by("id").descending());
        return accountRepository.findAccountByCurrencyAndMoney(pageable, currency, fromSum, upToSum).toList();
    }

    public Account findById(Long id) throws NotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        return account.get();
    }

    public List<Account> findByUserId(Long id) {
        return accountRepository.findAllByClientId(id);
    }

    public Account findByNumber(String number) throws NotFoundException {
        Optional<Account> account = accountRepository.findByNumber(number);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        return account.get();
    }

    public Account findByIndexForUser(User user, int index) throws NotFoundException {
        List<Account> accounts = accountRepository.findAllByClientId(user.getId());
        try {
            return accounts.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException("Account not found");
        }
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
        account.setNumber(generatedNumberAccount(account));
        return accountRepository.save(account);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    public void deleteByNumber(String number) throws NotFoundException {
        Optional<Account> account = accountRepository.findByNumber(number);
        if (account.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        accountRepository.deleteById(account.get().getId());
    }


    public boolean accountIsPresent(long id) {
        return accountRepository.findById(id).isPresent();
    }

    private String generatedNumberAccount(Account account) {
        char[] chars = account.getCurrency().getCode().toCharArray();
        int currencyCode = 0;
        for (int i = 0; i < chars.length; i++) {
            currencyCode += chars[i];
        }

        String firstNumber = String.valueOf(currencyCode);
        LocalDate date = LocalDate.now();
        String secondNumber = String.valueOf(date.getYear()*2 - 27);
        //Добавить количество закрытых аккаунтов
        int number = accountRepository.findAll().size();
        System.out.println(number);
        while (number < 1000_0000) {
            number = number*10;
        }
        String thirdNumber = String.valueOf(number);
        return firstNumber + secondNumber + thirdNumber;
    }

}
