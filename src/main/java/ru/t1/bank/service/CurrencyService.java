package ru.t1.bank.service;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;
import ru.t1.bank.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findById(Long id) throws NotFoundException {
        Optional<Currency> currency = currencyRepository.findById(id);
        if (currency.isEmpty()) {
            throw new NotFoundException("Currency not found");
        }
        return currency.get();
    }

    public Currency findByCode(String code) throws NotFoundException {
        Optional<Currency> currency = currencyRepository.findByCode(code);
        if (currency.isEmpty()) {
            throw new NotFoundException("Currency not found");
        }
        return currency.get();
    }


    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency createCurrency(String name, String code) {
        Currency currency = new Currency();
        currency.setName(name);
        currency.setCode(code);
        return currencyRepository.save(currency);
    }

    public void deleteById(Long id) throws NotFoundException {
        if (currencyRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Currency not found");
        }
        currencyRepository.deleteById(id);
    }

    public boolean currencyIsPresent(long id) {
        return currencyRepository.findById(id).isPresent();
    }

}
