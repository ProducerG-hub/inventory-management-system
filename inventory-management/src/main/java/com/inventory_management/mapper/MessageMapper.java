package com.inventory_management.mapper;

import com.inventory_management.dto.response.MessageResponseDTO;
import com.inventory_management.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "conversation.conversationId", target = "conversationId")
    @Mapping(source = "sender.userId", target = "senderId")
    @Mapping(source = "sender.fullName", target = "senderName")
    MessageResponseDTO toResponseDTO(Message message);
}