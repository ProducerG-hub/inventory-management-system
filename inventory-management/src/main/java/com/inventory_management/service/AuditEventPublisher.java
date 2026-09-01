package com.inventory_management.service;

import com.inventory_management.event.AuditEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@RequiredArgsConstructor
public class AuditEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final AuditRequestContext auditRequestContext;

    public void publish(AuditEvent event) {

        eventPublisher.publishEvent(
                AuditEvent.builder()
                        .user(event.getUser())
                        .action(event.getAction())
                        .entityType(event.getEntityType())
                        .entityId(event.getEntityId())
                        .description(event.getDescription())
                        .oldValues(event.getOldValues())
                        .newValues(event.getNewValues())
                        .ipAddress(toInetAddress(
                                auditRequestContext.getIpAddress()
                        ))
                        .userAgent(auditRequestContext.getUserAgent())
                        .build()
        );
    }

    private InetAddress toInetAddress(String ipAddress) {

        if (ipAddress == null || ipAddress.isBlank()) {
            return null;
        }

        try {
            return InetAddress.getByName(ipAddress);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid IP address: " + ipAddress,
                    e
            );
        }
    }
}