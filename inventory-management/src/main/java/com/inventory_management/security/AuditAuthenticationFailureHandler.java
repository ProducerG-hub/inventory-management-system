package com.inventory_management.security;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuditAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    private final AuditEventPublisher auditEventPublisher;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(null)
                        .action(AuditAction.LOGIN_FAILED)
                        .entityType(AuditEntityType.AUTHENTICATION)
                        .entityId(null)
                        .description("User login failed")
                        .eventType(AuditEventType.SECURITY)
                        .build()
        );
    }
}