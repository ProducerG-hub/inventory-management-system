package com.inventory_management.controller;

import com.inventory_management.dto.request.MessageRequestDTO;
import com.inventory_management.dto.response.MessageResponseDTO;
import com.inventory_management.entity.Conversation;
import com.inventory_management.entity.ConversationParticipant;
import com.inventory_management.repository.ConversationParticipantRepository;
import com.inventory_management.repository.ConversationRepository;
import com.inventory_management.service.MessageService;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

    private final MessageService messageService;

    private final ConversationRepository conversationRepository;

    private final ConversationParticipantRepository participantRepository;

    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/messages")
    public void sendMessage(
            MessageRequestDTO request,
            SimpMessageHeaderAccessor headerAccessor
    ) {

        /*
         * ============================================
         * GET WEBSOCKET SESSION USER ID
         * ============================================
         */

        Map<String, Object> sessionAttributes =
                headerAccessor.getSessionAttributes();


        if (sessionAttributes == null) {

            throw new IllegalStateException(
                    "WebSocket session attributes are missing"
            );

        }


        Object userIdObject =
                sessionAttributes.get("userId");


        if (userIdObject == null) {

            throw new IllegalStateException(
                    "WebSocket user ID not found in session"
            );

        }


        Integer currentUserId =
                (Integer) userIdObject;


        /*
         * ============================================
         * SAVE MESSAGE
         * ============================================
         */

        MessageResponseDTO message =
                messageService.sendMessage(
                        currentUserId,
                        request
                );


        /*
         * ============================================
         * FIND CONVERSATION
         * ============================================
         */

        Conversation conversation =
                conversationRepository
                        .findById(
                                request.getConversationId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Conversation not found"
                                )
                        );


        /*
         * ============================================
         * FIND PARTICIPANTS
         * ============================================
         */

        for (
                ConversationParticipant participant :
                participantRepository
                        .findByConversationConversationId(
                                conversation.getConversationId()
                        )
        ) {

            String recipientPrincipal =
                    participant
                            .getUser()
                            .getEmail();

            messagingTemplate.convertAndSendToUser(
                    recipientPrincipal,
                    "/queue/messages",
                    message
            );
        }
    }
}