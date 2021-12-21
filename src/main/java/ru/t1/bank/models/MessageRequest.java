package ru.t1.bank.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MessageRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne
    private User user;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private SupportRequest supportRequest;

}
