package ru.t1.bank.models;

import ru.t1.bank.enums.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private LocalDate dateOfBirth;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts;
}
