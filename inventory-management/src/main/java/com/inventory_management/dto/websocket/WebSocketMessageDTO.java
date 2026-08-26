package com.inventory_management.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageDTO {

    private Integer messageId;

    private Integer conversationId;

    private Integer senderId;

    private String senderName;

    private String messageContent;

    private Boolean isRead;

    private String sentAt;

}