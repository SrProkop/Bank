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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SupportRequest getSupportRequest() {
        return supportRequest;
    }

    public void setSupportRequest(SupportRequest supportRequest) {
        this.supportRequest = supportRequest;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", date=" + date +
                ", supportRequest=" + supportRequest +
                '}';
    }
}
