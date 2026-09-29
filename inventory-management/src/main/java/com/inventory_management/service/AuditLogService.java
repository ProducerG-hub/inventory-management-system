package com.inventory_management.service;

import com.inventory_management.dto.audit.AuditLogDetailResponseDTO;
import com.inventory_management.dto.audit.AuditLogResponseDTO;
import com.inventory_management.entity.AuditLog;
import com.inventory_management.event.AuditEvent;
import org.springframework.data.domain.Page;

public interface AuditLogService {

    AuditLog save(AuditLog auditLog);

    void record(AuditEvent event);

    Page<AuditLogResponseDTO> getAllAuditLogs(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    AuditLogDetailResponseDTO getAuditLogById(Long auditId);
}