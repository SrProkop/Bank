package ru.t1.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.bank.models.MessageRequest;
import ru.t1.bank.models.SupportRequest;
import ru.t1.bank.repository.MessageRequestRepository;
import ru.t1.bank.repository.SupportRequestRepository;

import java.util.List;

@Service
public class MessageRequestService {

    @Autowired
    MessageRequestRepository messageRequestRepository;

    public MessageRequestService(MessageRequestRepository messageRequestRepository) {
        this.messageRequestRepository = messageRequestRepository;
    }

    public void createMessageRequest(MessageRequest messageRequest) {
        messageRequestRepository.save(messageRequest);
    }

    public List<MessageRequest> findAll() {
        return messageRequestRepository.findAll();
    }

    public MessageRequest findById(Long id) {
        return messageRequestRepository.findById(id).orElse(null);
    }

}
