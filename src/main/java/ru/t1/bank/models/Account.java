package ru.t1.bank.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
    private BigDecimal money;
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Transaction> transactionsFrom;
    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Transaction> transactionsTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
        client.getAccounts().add(this);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Set<Transaction> getTransactionsFrom() {
        if (this.transactionsFrom == null) {
            this.transactionsFrom = new HashSet<>();
        }
        return transactionsFrom;
    }

    public Set<Transaction> getTransactionsTo() {
        if (this.transactionsTo == null) {
            this.transactionsTo = new HashSet<>();
        }
        return transactionsTo;
    }

}