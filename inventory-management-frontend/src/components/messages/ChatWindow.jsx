import { FiArrowLeft } from "react-icons/fi";

import MessageList from "./MessageList";
import MessageInput from "./MessageInput";

const ChatWindow = ({
    conversation,
    messages,
    currentUserId,
    onSend,
    onBack,
    loading
}) => {

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