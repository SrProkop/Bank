package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.models.Currency;
import ru.t1.bank.service.CurrencyService;

import java.util.List;

@RestController
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("currencies")
    public List<Currency> currencies() {
        return currencyService.findAll();
    }

    @PostMapping("/currencies/{id}")
    public Currency findCurrencyById(@PathVariable long id) {
        return currencyService.findById(id);
    }

    @PostMapping("/currencies/create")
    public String createCurrency(@RequestParam String name,
                                 @RequestParam String code) {
        currencyService.createCurrency(name, code);
        return "currency created";
    }

    @PostMapping("/currencies/delete/{id}")
    public String deleteCurrency(@PathVariable long id) {
        if (currencyService.currencyIsPresent(id)) {
            currencyService.deleteById(id);
            return "account deleted";
        } else {
            return "account not found";
        }
    }
}
