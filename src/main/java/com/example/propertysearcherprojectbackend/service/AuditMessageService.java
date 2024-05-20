package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.repository.AuditMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditMessageService {

    private final AuditMessageRepository auditMessageRepository;
    public void saveAuditMessage(AuditMessage auditMessage) {
        auditMessageRepository.save(auditMessage);
    }
}
