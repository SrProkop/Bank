package ru.t1.bank.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long number;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    @ManyToOne
    private Currency currency;
    private BigDecimal money;
    @OneToMany(mappedBy = "accountFrom")
    List<Transaction> transactionsFrom;
    @OneToMany(mappedBy = "accountTo")
    List<Transaction> transactionsTo;

}