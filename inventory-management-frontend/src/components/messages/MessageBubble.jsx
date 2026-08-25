const MessageBubble = ({
    message,
    currentUserId
}) => {

    const mine = message.senderId === currentUserId;

    const time = new Date(message.sentAt)
        .toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit"
        });

    return (
        <div
            className={`message-row ${
                mine ? "mine" : ""
            }`}
        >

            <div className="message-bubble">

                <span>
                    {message.deletedAt
                        ? "This message was deleted."
                        : message.messageContent}
                </span>

                <small>
                    {time}

                    {mine && (
                        <span className="message-status">
                            {message.isRead ? " ✓✓" : " ✓"}
                        </span>
                    )}
                </small>

            </div>

        </div>
    );
};

export default MessageBubble;