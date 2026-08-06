/*package com.IAM.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.IAM.entity.AuditLog;
import com.IAM.repository.AuditLogRepository;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog logAction(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

public List<AuditLog> getLogsByUser(Long userId) {
    return auditLogRepository.findByUserId(userId);
}
}
*/