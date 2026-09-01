package com.inventory_management.event;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.entity.User;
import lombok.Builder;
import lombok.Getter;
import java.net.InetAddress;

@Getter
@Builder
public class AuditEvent {

    private final User user;

    private final AuditAction action;

    private final AuditEntityType entityType;

    private final Integer entityId;

    private final String description;

    private final String oldValues;

    private final String newValues;

    private final InetAddress ipAddress;

    private final String userAgent;
}