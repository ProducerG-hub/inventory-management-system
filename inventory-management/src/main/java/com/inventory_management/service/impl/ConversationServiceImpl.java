package com.inventory_management.service.impl;

import com.inventory_management.dto.request.ConversationRequestDTO;
import com.inventory_management.dto.response.ConversationResponseDTO;
import com.inventory_management.entity.Conversation;
import com.inventory_management.entity.ConversationParticipant;
import com.inventory_management.entity.ConversationParticipantId;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.ConversationMapper;
import com.inventory_management.mapper.MessageMapper;
import com.inventory_management.repository.ConversationParticipantRepository;
import com.inventory_management.repository.ConversationRepository;
import com.inventory_management.repository.MessageRepository;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.ConversationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public ConversationResponseDTO createConversation(
            Integer currentUserId,
            ConversationRequestDTO request
    ) {

        if (currentUserId.equals(request.getParticipantId())) {
            throw new IllegalArgumentException(
                    "You cannot create a conversation with yourself"
            );
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() ->
                        new RuntimeException("Current user not found")
                );

        User participantUser = userRepository.findById(
                request.getParticipantId()
        ).orElseThrow(() ->
                new RuntimeException("Participant user not found")
        );

        List<ConversationParticipant> existingParticipants =
                participantRepository.findConversationBetweenUsers(
                        currentUserId,
                        request.getParticipantId()
                );

        if (!existingParticipants.isEmpty()) {

            Conversation existingConversation =
                    existingParticipants.get(0).getConversation();

            ConversationResponseDTO response =
                    conversationMapper.toResponseDTO(existingConversation);

            response.setParticipantId(request.getParticipantId());

            User participantUser1 = userRepository
                    .findById(request.getParticipantId())
                    .orElseThrow(() ->
                            new RuntimeException("Participant user not found")
                    );

            response.setParticipantName(participantUser1.getFullName());

            return response;
        }

        Conversation conversation = Conversation.builder()
                .build();

        conversation = conversationRepository.save(conversation);

        ConversationParticipant participant1 =
                ConversationParticipant.builder()
                        .id(new ConversationParticipantId(
                                conversation.getConversationId(),
                                currentUser.getUserId()
                        ))
                        .conversation(conversation)
                        .user(currentUser)
                        .build();

        ConversationParticipant participant2 =
                ConversationParticipant.builder()
                        .id(new ConversationParticipantId(
                                conversation.getConversationId(),
                                participantUser.getUserId()
                        ))
                        .conversation(conversation)
                        .user(participantUser)
                        .build();

        participantRepository.save(participant1);
        participantRepository.save(participant2);

        ConversationResponseDTO response =
                conversationMapper.toResponseDTO(conversation);

        response.setParticipantId(participantUser.getUserId());
        response.setParticipantName(participantUser.getFullName());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDTO> getUserConversations(
            Integer currentUserId
    ) {

        return participantRepository
                .findByUserUserId(currentUserId)
                .stream()
                .map(ConversationParticipant::getConversation)
                .map(conversation -> {

                    ConversationResponseDTO response =
                            conversationMapper.toResponseDTO(conversation);

                    conversation.getParticipants()
                            .stream()
                            .filter(participant ->
                                    !participant.getUser().getUserId()
                                            .equals(currentUserId)
                            )
                            .findFirst()
                            .ifPresent(participant -> {
                                response.setParticipantId(
                                        participant.getUser().getUserId()
                                );

                                response.setParticipantName(
                                        participant.getUser().getFullName()
                                );
                            });

                    Integer conversationId =
                            conversation.getConversationId();

                    messageRepository
                            .findTopByConversationConversationIdOrderBySentAtDesc(
                                    conversationId
                            )
                            .ifPresent(message ->
                                    response.setLastMessage(
                                            messageMapper.toResponseDTO(message)
                                    )
                            );

                    response.setUnreadCount(
                            messageRepository.countUnreadMessages(
                                    conversationId,
                                    currentUserId
                            )
                    );

                    return response;
                })
                .sorted((a, b) ->
                        b.getUpdatedAt().compareTo(a.getUpdatedAt())
                )
                .toList();
    }
}