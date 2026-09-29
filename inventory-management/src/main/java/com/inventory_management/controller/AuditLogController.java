package com.inventory_management.controller;

import com.inventory_management.dto.audit.AuditLogDetailResponseDTO;
import com.inventory_management.dto.audit.AuditLogResponseDTO;
import com.inventory_management.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Admin audit trail APIs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get audit logs",
            description = "Returns the audit trail for admin review"
    )
    public ResponseEntity<Page<AuditLogResponseDTO>> getAllAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(
                auditLogService.getAllAuditLogs(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    @GetMapping("/audit-logs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get audit log by ID",
            description = "Returns a single audit log entry for investigation"
    )
    public ResponseEntity<AuditLogDetailResponseDTO> getAuditLogById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                auditLogService.getAuditLogById(id)
        );
    }
}