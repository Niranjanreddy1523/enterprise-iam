package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
