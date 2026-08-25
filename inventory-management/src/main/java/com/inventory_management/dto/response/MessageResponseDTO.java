package com.inventory_management.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponseDTO {

    private Integer messageId;

    private Integer conversationId;

    private Integer senderId;

    private String senderName;

    private String messageContent;

    private Boolean isRead;

    private LocalDateTime sentAt;

    private LocalDateTime deletedAt;
}