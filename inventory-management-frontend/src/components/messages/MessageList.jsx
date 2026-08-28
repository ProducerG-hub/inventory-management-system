import { useEffect, useRef, useState } from "react";
import { FiChevronDown } from "react-icons/fi";

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

    const [showScrollToLatest, setShowScrollToLatest] =
        useState(false);


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

        setShowScrollToLatest(
            distanceFromBottom > 160
        );

    };


    const scrollToBottom = () => {

        const container = containerRef.current;

        if (!container) {
            return;
        }

        container.scrollTop = container.scrollHeight;

        setShowScrollToLatest(false);

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

        if (shouldStickToBottomRef.current) {
            requestAnimationFrame(() => {
                scrollToBottom();
            });
        }

    }, [messages]);

    if (!messages.length) {
        return (
            <div className="empty-messages">
                <p>No messages yet.</p>
                <span>Start the conversation.</span>
            </div>
        );
    }

    return (
        <div className="chat-messages-wrapper">
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

            {showScrollToLatest && (
                <button
                    type="button"
                    className="scroll-to-latest"
                    onClick={scrollToBottom}
                    aria-label="Scroll to latest message"
                    title="Scroll to latest message"
                >
                    <FiChevronDown />
                    Latest
                </button>
            )}
        </div>
    );
};

export default MessageList;
