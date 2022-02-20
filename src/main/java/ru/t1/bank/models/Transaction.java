package ru.t1.bank.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.t1.bank.enums.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreatedDate
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    @ManyToOne
    private Account accountFrom;
    @ManyToOne
    private Account accountTo;
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

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", sumFrom=" + sumFrom +
                ", sumTo=" + sumTo +
                '}';
    }
}
