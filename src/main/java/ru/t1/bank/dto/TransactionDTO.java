package ru.t1.bank.dto;

import org.springframework.data.annotation.CreatedDate;
import ru.t1.bank.enums.Type;
import ru.t1.bank.models.Account;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private LocalDateTime date;
    private Type type;
    private String accountFrom;
    private String accountTo;
    private BigDecimal sumFrom;
    private BigDecimal sumTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getSumFrom() {
        return sumFrom;
    }

    public void setSumFrom(BigDecimal sumFrom) {
        this.sumFrom = sumFrom;
    }

    public BigDecimal getSumTo() {
        return sumTo;
    }

    public void setSumTo(BigDecimal sumTo) {
        this.sumTo = sumTo;
    }
}
