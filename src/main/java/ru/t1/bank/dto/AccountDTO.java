package ru.t1.bank.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ru.t1.bank.models.Currency;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.models.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class AccountDTO {

    private Long id;
    private String number;
    private UserDTO client;
    private CurrencyDTO currency;
    private boolean isOpen;
    private BigDecimal money;
    Set<TransactionDTO> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
