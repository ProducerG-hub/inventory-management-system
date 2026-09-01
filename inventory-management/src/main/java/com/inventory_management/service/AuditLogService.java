package com.inventory_management.service;

import com.inventory_management.entity.AuditLog;
import com.inventory_management.event.AuditEvent;

public interface AuditLogService {

    AuditLog save(AuditLog auditLog);
    void record(AuditEvent event);
}