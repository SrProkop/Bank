package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.models.SupportRequest;
import ru.t1.bank.models.Transaction;
import ru.t1.bank.repository.SupportRequestRepository;
import ru.t1.bank.repository.TransactionRepository;

import java.util.List;

@Service
public class SupportRequestService {

    SupportRequestRepository supportRequestRepository;

    public SupportRequestService(SupportRequestRepository supportRequestRepository) {
        this.supportRequestRepository = supportRequestRepository;
    }

    public void createSupportRequest(SupportRequest supportRequest) {
        supportRequestRepository.save(supportRequest);
    }

    public List<SupportRequest> findAll() {
        return supportRequestRepository.findAll();
    }

    public SupportRequest findById(Long id) {
        return supportRequestRepository.findById(id).orElse(null);
    }

}
