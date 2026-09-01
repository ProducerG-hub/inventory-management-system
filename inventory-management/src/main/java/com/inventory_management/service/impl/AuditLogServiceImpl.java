package com.inventory_management.service.impl;

import com.inventory_management.entity.AuditLog;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.repository.AuditLogRepository;
import com.inventory_management.service.AuditLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional
    public AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional
    public void record(AuditEvent event) {

        AuditLog auditLog = AuditLog.builder()
                .user(event.getUser())
                .action(event.getAction())
                .entityType(event.getEntityType())
                .entityId(event.getEntityId())
                .description(event.getDescription())
                .oldValues(event.getOldValues())
                .newValues(event.getNewValues())
                .ipAddress(event.getIpAddress())
                .userAgent(event.getUserAgent())
                .build();

        auditLogRepository.save(auditLog);
    }
}