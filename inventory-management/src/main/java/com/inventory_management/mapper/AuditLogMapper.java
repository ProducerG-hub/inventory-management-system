package com.inventory_management.mapper;

import com.inventory_management.dto.audit.AuditLogDetailResponseDTO;
import com.inventory_management.dto.audit.AuditLogResponseDTO;
import com.inventory_management.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = UserMapper.class
)
public interface AuditLogMapper {

    AuditLogResponseDTO toResponse(AuditLog auditLog);

    @Mapping(
            target = "ipAddress",
            expression = "java(auditLog.getIpAddress() != null ? auditLog.getIpAddress().getHostAddress() : null)"
    )
    AuditLogDetailResponseDTO toDetailResponse(AuditLog auditLog);
}