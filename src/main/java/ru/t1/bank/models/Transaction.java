package ru.t1.bank.models;

import ru.t1.bank.enums.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    private Account accountFrom;
    @ManyToOne
    private Account accountTo;
    private BigDecimal sumFrom;
    private BigDecimal sumTo;

}
