package ru.t1.bank.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.bank.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Where(clause = "is_open = true")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private User client;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
    private boolean isOpen;
    private BigDecimal money;
    private LocalDate dateOpen;
    private LocalDate dateClose;
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Transaction> transactionsFrom;
    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Transaction> transactionsTo;

    public Account() {
    }

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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public LocalDate getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(LocalDate dateOpen) {
        this.dateOpen = dateOpen;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
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