package ru.t1.bank.controllers;

import org.springframework.web.bind.annotation.*;
import ru.t1.bank.Response;
import ru.t1.bank.dto.CurrencyDTO;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.mappers.CurrencyMapper;
import ru.t1.bank.models.Currency;
import ru.t1.bank.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/admin/currency")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    public CurrencyController(CurrencyService currencyService,
                              CurrencyMapper currencyMapper) {
        this.currencyService = currencyService;
        this.currencyMapper = currencyMapper;
    }

    @GetMapping
    public List<CurrencyDTO> getCurrencies() {
        return currencyMapper.toCurrencyDTO(currencyService.findAll());
    }

    @GetMapping("/{id}")
    public CurrencyDTO getCurrencyById(@PathVariable long id) throws NotFoundException {
        return currencyMapper.toCurrencyDTO(currencyService.findById(id));
    }

    @PutMapping
    public CurrencyDTO createCurrency(@RequestBody Currency currency) {
        return currencyMapper.toCurrencyDTO(currencyService.createCurrency(currency));
    }

    @DeleteMapping("/{id}")
    public Response deleteCurrency(@PathVariable long id) throws NotFoundException {
        currencyService.deleteById(id);
        return new Response("Currency deleted");
    }
}