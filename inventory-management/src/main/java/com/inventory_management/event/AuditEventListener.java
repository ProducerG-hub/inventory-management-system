package com.inventory_management.event;

import com.inventory_management.audit.AuditEventType;
import com.inventory_management.entity.AuditLog;
import com.inventory_management.service.AuditLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventListener {

    private final AuditLogService auditLogService;

    // Security events
    @EventListener
    public void handleSecurityEvent(AuditEvent event) {

        if (event.getEventType() != AuditEventType.SECURITY) {
            return;
        }

        saveAuditLog(event);
    }

    // Business events
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBusinessEvent(AuditEvent event) {

        if (event.getEventType() != AuditEventType.BUSINESS) {
            return;
        }

        saveAuditLog(event);
    }

    private void saveAuditLog(AuditEvent event) {

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