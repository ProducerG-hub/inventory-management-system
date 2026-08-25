package com.inventory_management.repository;

import com.inventory_management.entity.ConversationParticipant;
import com.inventory_management.entity.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ConversationParticipantRepository
        extends JpaRepository<ConversationParticipant, ConversationParticipantId> {

    List<ConversationParticipant> findByUserUserId(Integer userId);

    @Query("""
    SELECT cp
    FROM ConversationParticipant cp
    WHERE cp.conversation.conversationId IN (
        SELECT cp1.conversation.conversationId
        FROM ConversationParticipant cp1
        WHERE cp1.user.userId = :user1Id
    )
    AND cp.user.userId = :user2Id
""")
    List<ConversationParticipant> findConversationBetweenUsers(
            @Param("user1Id") Integer user1Id,
            @Param("user2Id") Integer user2Id
    );
}