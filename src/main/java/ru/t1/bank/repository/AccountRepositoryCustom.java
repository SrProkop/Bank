package ru.t1.bank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepositoryCustom {

    Page<Account> findAccountByCurrencyAndMoney(Pageable pageable,
                                                Currency currency,
                                                BigDecimal moneyMore,
                                                BigDecimal moneyLess) throws NotFoundException;

}
