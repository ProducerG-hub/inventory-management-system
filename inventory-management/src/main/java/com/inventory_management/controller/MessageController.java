package com.inventory_management.controller;

import com.inventory_management.dto.request.MessageRequestDTO;
import com.inventory_management.dto.response.MessageResponseDTO;
import com.inventory_management.security.CustomUserDetails;
import com.inventory_management.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<MessageResponseDTO> sendMessage(
            Authentication authentication,
            @Valid @RequestBody MessageRequestDTO request
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Integer currentUserId =
                userDetails.getUser().getUserId();
        MessageResponseDTO response =
                messageService.sendMessage(
                        currentUserId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/conversation/{conversationId}")
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<MessageResponseDTO>> getMessages(
            Authentication authentication,
            @PathVariable Integer conversationId
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Integer currentUserId =
                userDetails.getUser().getUserId();
        return ResponseEntity.ok(
                messageService.getConversationMessages(
                        currentUserId,
                        conversationId
                )
        );
    }

    @PatchMapping("/conversation/{conversationId}/read")
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<Void> markMessagesAsRead(
            Authentication authentication,
            @PathVariable Integer conversationId
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Integer currentUserId =
                userDetails.getUser().getUserId();

        messageService.markMessagesAsRead(
                currentUserId,
                conversationId
        );

        return ResponseEntity.noContent().build();
    }
}