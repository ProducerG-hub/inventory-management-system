package com.inventory_management.event;

import com.inventory_management.entity.AuditLog;
import com.inventory_management.service.AuditLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventListener {

    private final AuditLogService auditLogService;

    @EventListener
    public void handleAuditEvent(AuditEvent event) {


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

        auditLogService.save(auditLog);
    }
           

}