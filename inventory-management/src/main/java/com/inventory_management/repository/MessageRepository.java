package com.inventory_management.repository;

import com.inventory_management.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByConversationConversationIdOrderBySentAtAsc(
            Integer conversationId
    );

    @Query("""
        SELECT COUNT(m)
        FROM Message m
        WHERE m.conversation.conversationId = :conversationId
        AND m.isRead = false
        AND m.sender.userId <> :userId
        AND m.deletedAt IS NULL
    """)
    Long countUnreadMessages(
            @Param("conversationId") Integer conversationId,
            @Param("userId") Integer userId
    );

    Optional<Message> findTopByConversationConversationIdOrderBySentAtDesc(
            Integer conversationId
    );
}