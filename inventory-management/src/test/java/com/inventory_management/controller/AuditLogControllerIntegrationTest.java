package com.inventory_management.controller;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.entity.AuditLog;
import com.inventory_management.entity.User;
import com.inventory_management.repository.AuditLogRepository;
import com.inventory_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:auditlogdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=always",
        "spring.jpa.defer-datasource-initialization=true",
        "jwt.secret=VGhpc0lzQVN1cGVyU2VjdXJlS2V5Rm9yTXlJbnZlbnRvcnlNYW5hZ2VtZW50U3lzdGVtMjAyNg=="
})
class AuditLogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        auditLogRepository.deleteAll();
        userRepository.deleteAll();

        User actor = new User();
        actor.setFullName("Admin User");
        actor.setEmail("admin@example.com");
        actor.setPassword("hashed-password");
        actor.setRole("ADMIN");
        actor.setIsActive(true);
        actor.setCreatedAt(LocalDateTime.now());
        userRepository.save(actor);

        User target = new User();
        target.setFullName("Staff User");
        target.setEmail("staff@example.com");
        target.setPassword("hashed-password");
        target.setRole("STAFF");
        target.setIsActive(true);
        target.setCreatedAt(LocalDateTime.now());
        userRepository.save(target);

        AuditLog auditLog = AuditLog.builder()
                .user(actor)
                .action(AuditAction.CREATE)
                .entityType(AuditEntityType.USER)
                .entityId(target.getUserId())
                .description("User created successfully")
                .oldValues(null)
                .newValues("{\"fullName\":\"Staff User\"}")
                .eventType(AuditEventType.BUSINESS)
                .ipAddress(null)
                .userAgent("JUnit Test Agent")
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void adminCanFetchAuditLogsPage() throws Exception {
        mockMvc.perform(get("/api/audit-logs")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("sortDir", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].entityType").value("USER"))
                .andExpect(jsonPath("$.content[0].userFullName").value("Admin User"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void adminCanFetchAuditLogById() throws Exception {
        Long id = auditLogRepository.findAll().getFirst().getAuditId();

        mockMvc.perform(get("/api/audit-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditId").value(id.intValue()))
                .andExpect(jsonPath("$.action").value("CREATE"));
    }

    @Test
    @WithMockUser(username = "staff@example.com", roles = "STAFF")
    void staffCannotFetchAuditLogs() throws Exception {
        mockMvc.perform(get("/api/audit-logs"))
                .andExpect(status().isForbidden());
    }
}
