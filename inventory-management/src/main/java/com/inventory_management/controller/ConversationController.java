package com.inventory_management.controller;

import com.inventory_management.dto.request.ConversationRequestDTO;
import com.inventory_management.dto.response.ConversationResponseDTO;
import com.inventory_management.security.CustomUserDetails;
import com.inventory_management.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ConversationResponseDTO> createConversation(
            Authentication authentication,
            @Valid @RequestBody ConversationRequestDTO request
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Integer currentUserId =
                userDetails.getUser().getUserId();
        ConversationResponseDTO response =
                conversationService.createConversation(
                        currentUserId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<ConversationResponseDTO>> getUserConversations(
            Authentication authentication
    ) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Integer currentUserId =
                userDetails.getUser().getUserId();
        return ResponseEntity.ok(
                conversationService.getUserConversations(
                        currentUserId
                )
        );
    }
}