package ru.t1.bank.models;

import ru.t1.bank.enums.Stage;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "supportRequest", fetch = FetchType.EAGER)
    private Set<MessageRequest> messages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Set<MessageRequest> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageRequest> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "SupportRequest{" +
                "id=" + id +
                ", client=" + client +
                ", employee=" + employee +
                ", question='" + question + '\'' +
                ", stage=" + stage +
                ", messages=" + messages +
                '}';
    }
}
