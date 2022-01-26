package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByCode(String code);

}
