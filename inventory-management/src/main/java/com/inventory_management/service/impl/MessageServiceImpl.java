package com.inventory_management.service.impl;

import com.inventory_management.dto.request.MessageRequestDTO;
import com.inventory_management.dto.response.MessageResponseDTO;
import com.inventory_management.entity.Conversation;
import com.inventory_management.entity.Message;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.MessageMapper;
import com.inventory_management.repository.ConversationParticipantRepository;
import com.inventory_management.repository.ConversationRepository;
import com.inventory_management.repository.MessageRepository;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.MessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponseDTO sendMessage(
            Integer currentUserId,
            MessageRequestDTO request
    ) {

        Conversation conversation = conversationRepository
                .findById(request.getConversationId())
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found")
                );

        boolean isParticipant = participantRepository.existsById(
                new com.inventory_management.entity.ConversationParticipantId(
                        conversation.getConversationId(),
                        currentUserId
                )
        );

        if (!isParticipant) {
            throw new RuntimeException(
                    "You are not a participant of this conversation"
            );
        }

        User sender = userRepository.findById(currentUserId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .messageContent(request.getMessageContent())
                .isRead(false)
                .build();

        message = messageRepository.save(message);

        conversation.setUpdatedAt(
                java.time.LocalDateTime.now()
        );

        conversationRepository.save(conversation);

        return messageMapper.toResponseDTO(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDTO> getConversationMessages(
            Integer currentUserId,
            Integer conversationId
    ) {

        boolean isParticipant = participantRepository.existsById(
                new com.inventory_management.entity.ConversationParticipantId(
                        conversationId,
                        currentUserId
                )
        );

        if (!isParticipant) {
            throw new RuntimeException(
                    "You are not a participant of this conversation"
            );
        }

        return messageRepository
                .findByConversationConversationIdOrderBySentAtAsc(
                        conversationId
                )
                .stream()
                .map(messageMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void markMessagesAsRead(
            Integer currentUserId,
            Integer conversationId
    ) {

        boolean isParticipant = participantRepository.existsById(
                new com.inventory_management.entity.ConversationParticipantId(
                        conversationId,
                        currentUserId
                )
        );

        if (!isParticipant) {
            throw new RuntimeException(
                    "You are not a participant of this conversation"
            );
        }

        List<Message> messages =
                messageRepository
                        .findByConversationConversationIdOrderBySentAtAsc(
                                conversationId
                        );

        messages.stream()
                .filter(message ->
                        !message.getSender().getUserId().equals(currentUserId)
                )
                .filter(message ->
                        !message.getIsRead()
                )
                .forEach(message ->
                        message.setIsRead(true)
                );

        messageRepository.saveAll(messages);
    }
}