package com.inventory_management.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageRequestDTO {

    private Integer conversationId;

    private String messageContent;

}