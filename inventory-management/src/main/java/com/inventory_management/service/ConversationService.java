package com.inventory_management.service;

import com.inventory_management.dto.request.ConversationRequestDTO;
import com.inventory_management.dto.response.ConversationResponseDTO;

import java.util.List;

public interface ConversationService {

    ConversationResponseDTO createConversation(
            Integer currentUserId,
            ConversationRequestDTO request
    );

    List<ConversationResponseDTO> getUserConversations(
            Integer currentUserId
    );
}