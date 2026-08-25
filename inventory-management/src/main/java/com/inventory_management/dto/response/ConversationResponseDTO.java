package com.inventory_management.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponseDTO {

    private Integer conversationId;

    private Integer participantId;

    private String participantName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MessageResponseDTO lastMessage;

    private Long unreadCount;
}