package com.inventory_management.mapper;

import com.inventory_management.dto.response.ConversationResponseDTO;
import com.inventory_management.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(target = "participantId", ignore = true)
    @Mapping(target = "participantName", ignore = true)
    @Mapping(target = "lastMessage", ignore = true)
    @Mapping(target = "unreadCount", ignore = true)
    ConversationResponseDTO toResponseDTO(Conversation conversation);
}