import { useEffect, useRef } from "react";

import MessageBubble from "./MessageBubble";

const MessageList = ({
    messages,
    currentUserId,
    conversationId,
    forceScrollTrigger
}) => {

    const containerRef = useRef(null);

    const shouldStickToBottomRef = useRef(true);

    const pendingForcedScrollRef = useRef(false);


    const updateStickToBottom = () => {

        const container = containerRef.current;

        if (!container) {
            return;
        }

        const distanceFromBottom =
            container.scrollHeight -
            container.scrollTop -
            container.clientHeight;

        shouldStickToBottomRef.current =
            distanceFromBottom <= 80;

    };


    const scrollToBottom = () => {

        const container = containerRef.current;

        if (!container) {
            return;
        }

        container.scrollTop = container.scrollHeight;

    };


    useEffect(() => {

        shouldStickToBottomRef.current = true;

        requestAnimationFrame(() => {
            scrollToBottom();
        });

    }, [conversationId]);


    useEffect(() => {

        pendingForcedScrollRef.current = true;

        shouldStickToBottomRef.current = true;

        requestAnimationFrame(() => {
            scrollToBottom();
        });

    }, [forceScrollTrigger]);


    useEffect(() => {

        if (pendingForcedScrollRef.current) {

            pendingForcedScrollRef.current = false;

            requestAnimationFrame(() => {
                scrollToBottom();
            });

            return;

        }

        const lastMessage =
            messages[messages.length - 1];

        const sentByCurrentUser =
            lastMessage?.senderId === currentUserId;

        if (shouldStickToBottomRef.current || sentByCurrentUser) {
            requestAnimationFrame(() => {
                scrollToBottom();
            });
        }

    }, [messages, currentUserId]);

    if (!messages.length) {
        return (
            <div className="empty-messages">
                <p>No messages yet.</p>
                <span>Start the conversation.</span>
            </div>
        );
    }

    return (
        <div
            className="chat-messages"
            ref={containerRef}
            onScroll={updateStickToBottom}
        >

            <div className="date-divider">
                <span>Messages</span>
            </div>

            {messages.map((message) => (
                <MessageBubble
                    key={message.messageId}
                    message={message}
                    currentUserId={currentUserId}
                />
            ))}

        </div>
    );
};

export default MessageList;