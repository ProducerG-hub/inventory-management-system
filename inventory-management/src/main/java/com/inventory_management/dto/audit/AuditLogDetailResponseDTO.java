package com.inventory_management.dto.audit;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDetailResponseDTO {

    private Long auditId;
    private UserSummaryDTO user;
    private AuditAction action;
    private AuditEntityType entityType;
    private Integer entityId;
    private String description;
    private AuditEventType eventType;
    private String oldValues;
    private String newValues;
    private String ipAddress;
    private String userAgent;
    private Instant createdAt;
}