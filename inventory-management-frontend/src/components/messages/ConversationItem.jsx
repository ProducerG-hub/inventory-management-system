import PropTypes from "prop-types";

const ConversationItem = ({
    conversation,
    isSelected,
    onSelect
}) => {

    const initials = conversation.participantName
        ?.split(" ")
        .map((name) => name[0])
        .join("")
        .slice(0, 2)
        .toUpperCase();

    return (
        <button
            type="button"
            className={`conversation-item ${
                isSelected ? "selected" : ""
            }`}
            onClick={() => onSelect(conversation)}
        >

            <span className="avatar blue">
                {initials}
            </span>

            <span className="conversation-copy">

                <strong>
                    {conversation.participantName}
                </strong>

                <small>
                    {conversation.lastMessage
                        ?.messageContent || "No messages yet"}
                </small>

            </span>

            {conversation.unreadCount > 0 && (
                <span className="unread-count">
                    {conversation.unreadCount}
                </span>
            )}

        </button>
    );
};

ConversationItem.propTypes = {
    conversation: PropTypes.object.isRequired,
    isSelected: PropTypes.bool.isRequired,
    onSelect: PropTypes.func.isRequired
};

export default ConversationItem;