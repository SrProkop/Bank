package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.models.Currency;
import ru.t1.bank.repository.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void createCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    public void createCurrency(String name, String code) {
        Currency currency = new Currency();
        currency.setName(name);
        currency.setCode(code);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Currency findById(Long id) {
        return currencyRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public boolean currencyIsPresent(long id) {
        return currencyRepository.findById(id).isPresent();
    }

}
