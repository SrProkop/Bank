package ru.t1.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Currency;
import ru.t1.bank.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/admin/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> currencies() {
        return currencyService.findAll();
    }

    @GetMapping("/{id}")
    public Currency findCurrencyById(@PathVariable long id) throws NotFoundException {
        return currencyService.findById(id);
    }

    @PostMapping
    public Currency createCurrency(@RequestParam String name,
                                 @RequestParam String code) {
        return currencyService.createCurrency(name, code);
    }

    @DeleteMapping("/{id}")
    public Response deleteCurrency(@PathVariable long id) throws NotFoundException {
        currencyService.deleteById(id);
        return new Response("Currency deleted");
    }
}
