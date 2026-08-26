import { useState } from "react";
import { FiSend } from "react-icons/fi";

const MessageInput = ({
    conversationId,
    onSend,
    disabled = false
}) => {

    const [draft, setDraft] = useState("");

    const handleSubmit = async (event) => {

        event.preventDefault();

        const message = draft.trim();

        if (!message || disabled) {
            return;
        }

        await onSend(conversationId, message);

        setDraft("");
    };

    return (
        <form
            className="message-composer"
            onSubmit={handleSubmit}
        >

            <input
                value={draft}
                onChange={(event) =>
                    setDraft(event.target.value)
                }
                placeholder="Write a message..."
                aria-label="Write a message"
                disabled={disabled}
            />

            <button
                type="submit"
                disabled={disabled || !draft.trim()}
                aria-label="Send message"
                title="Send message"
            >
                <FiSend />
            </button>

        </form>
    );
};

export default MessageInput;