package com.inventory_management.service.impl;

import com.inventory_management.dto.audit.AuditLogDetailResponseDTO;
import com.inventory_management.dto.audit.AuditLogResponseDTO;
import com.inventory_management.entity.AuditLog;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.exception.ResourceNotFoundException;
import com.inventory_management.mapper.AuditLogMapper;
import com.inventory_management.repository.AuditLogRepository;
import com.inventory_management.service.AuditLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

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
                .eventType(event.getEventType())
                .build();

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponseDTO> getAllAuditLogs(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return auditLogRepository.findAll(pageable)
                .map(auditLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditLogDetailResponseDTO getAuditLogById(Long auditId) {

        AuditLog auditLog = auditLogRepository.findById(auditId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Audit log not found")
                );

        return auditLogMapper.toDetailResponse(auditLog);
    }
}