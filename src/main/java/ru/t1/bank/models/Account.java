package ru.t1.bank.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE accounts SET is_open = false WHERE id = ?")
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
    @Min(0)
    private BigDecimal money;
    @CreatedDate
    private LocalDate dateOpen;
    @LastModifiedDate
    private LocalDate lastModifiedDate;
    @LastModifiedBy
    private Long lastModifiedBy;
    @JsonIgnore
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Transaction> transactionsFrom;
    @JsonIgnore
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

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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