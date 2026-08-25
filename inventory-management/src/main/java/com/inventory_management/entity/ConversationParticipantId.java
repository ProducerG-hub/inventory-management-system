package com.inventory_management.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConversationParticipantId implements Serializable {

    private Integer conversationId;

    private Integer userId;
}