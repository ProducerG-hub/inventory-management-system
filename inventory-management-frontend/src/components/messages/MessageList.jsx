import MessageBubble from "./MessageBubble";

const MessageList = ({
    messages,
    currentUserId
}) => {

    if (!messages.length) {
        return (
            <div className="empty-messages">
                <p>No messages yet.</p>
                <span>Start the conversation.</span>
            </div>
        );
    }

    return (
        <div className="chat-messages">

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