package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.MessageRequest;

public interface MessageRequestRepository extends JpaRepository<MessageRequest, Long> {
}
