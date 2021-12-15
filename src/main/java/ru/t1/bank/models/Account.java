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
    private User client;
    @ManyToOne
    private Currency currency;
    private BigDecimal money;
    @OneToMany
    private List<Transaction> historyTransaction;

}
