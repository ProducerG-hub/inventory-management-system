package com.inventory_management.service;

import com.inventory_management.dto.request.MessageRequestDTO;
import com.inventory_management.dto.response.MessageResponseDTO;

import java.util.List;

public interface MessageService {

    MessageResponseDTO sendMessage(
            Integer currentUserId,
            MessageRequestDTO request
    );

    List<MessageResponseDTO> getConversationMessages(
            Integer currentUserId,
            Integer conversationId
    );

    void markMessagesAsRead(
            Integer currentUserId,
            Integer conversationId
    );
}