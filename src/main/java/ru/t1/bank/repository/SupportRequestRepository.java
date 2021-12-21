package ru.t1.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.bank.models.SupportRequest;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Long> {
}
