package ru.t1.bank.models;

import ru.t1.bank.enums.Stage;

import javax.persistence.*;
import java.util.List;

@Entity
public class SupportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User client;
    @ManyToOne
    private User employee;
    private String question;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    @OneToMany(mappedBy = "supportRequest")
    private List<MessageRequest> messages;

}
