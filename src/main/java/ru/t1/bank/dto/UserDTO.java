package ru.t1.bank.dto;

import ru.t1.bank.models.Account;
import ru.t1.bank.models.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Set<Role> roles;
    private Set<String> accounts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Role> getRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }

    public Set<String> getAccounts() {
        if (this.accounts == null) {
            this.accounts = new HashSet<>();
        }
        return accounts;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", accounts=" + accounts +
                '}';
    }
}
