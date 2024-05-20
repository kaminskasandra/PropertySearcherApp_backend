package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditMessageRepository extends JpaRepository<AuditMessage, Long> {
}
