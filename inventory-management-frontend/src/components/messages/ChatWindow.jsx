import { useEffect, useRef, useState } from "react";
import { FiArrowLeft, FiMoreVertical } from "react-icons/fi";

import MessageList from "./MessageList";
import MessageInput from "./MessageInput";

const ChatWindow = ({
    conversation,
    messages,
    currentUserId,
    onSend,
    onBack,
    loading,
    isWebSocketConnected,
    forceScrollTrigger,
    onClearChat
}) => {

    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const menuRef = useRef(null);


    useEffect(() => {

        const handleOutsideClick = (event) => {

            if (
                menuRef.current &&
                !menuRef.current.contains(event.target)
            ) {
                setIsMenuOpen(false);
            }

        };

        document.addEventListener("mousedown", handleOutsideClick);

        return () => {
            document.removeEventListener("mousedown", handleOutsideClick);
        };

    }, []);


    if (!conversation) {
        return (
            <div className="chat-panel chat-empty">
                <div>
                    <h2>Select a conversation</h2>
                    <p>
                        Choose a conversation to start messaging.
                    </p>
                </div>
            </div>
        );
    }

    const initials = conversation.participantName
        ?.split(" ")
        .map((name) => name[0])
        .join("")
        .slice(0, 2)
        .toUpperCase();

    return (
        <div className="chat-panel">

            <div className="chat-heading">

                <button
                    type="button"
                    className="chat-back-button"
                    onClick={onBack}
                    aria-label="Back to conversations"
                    title="Back to conversations"
                >
                    <FiArrowLeft />
                </button>

                <span className="avatar blue">
                    {initials}
                </span>

                <div>
                    <h2>
                        {conversation.participantName}
                    </h2>

                    <small className="chat-presence">
                        <span
                            className={isWebSocketConnected
                                ? "presence-dot online"
                                : "presence-dot"}
                            aria-hidden="true"
                        />
                        {isWebSocketConnected
                            ? "Live"
                            : "Reconnecting..."}
                    </small>
                </div>

                <div className="chat-header-actions" ref={menuRef}>

                    <button
                        type="button"
                        className="chat-menu-button"
                        onClick={() =>
                            setIsMenuOpen((current) => !current)
                        }
                        aria-label="Open chat actions"
                        title="Chat actions"
                    >
                        <FiMoreVertical />
                    </button>


                    {isMenuOpen && (
                        <div className="chat-menu-dropdown">

                            <button
                                type="button"
                                className="chat-menu-item"
                                onClick={() => {
                                    onClearChat?.(conversation.conversationId);
                                    setIsMenuOpen(false);
                                }}
                                disabled={!messages.length}
                            >
                                Clear chat
                            </button>

                        </div>
                    )}

                </div>

            </div>

            {loading ? (

                <div className="messages-loading">
                    Loading messages...
                </div>

            ) : (

                <MessageList
                    messages={messages}
                    currentUserId={currentUserId}
                    conversationId={conversation.conversationId}
                    forceScrollTrigger={forceScrollTrigger}
                />

            )}

            <MessageInput
                conversationId={conversation.conversationId}
                onSend={onSend}
                disabled={loading}
            />

        </div>
    );
};

export default ChatWindow;
