import { useMemo, useState } from "react";
import {
    FiEdit3,
    FiPlus,
    FiSearch,
    FiUsers
} from "react-icons/fi";

import ConversationItem from "./ConversationItem";

const ConversationList = ({
    conversations,
    activeId,
    onSelect,
    onNewConversation
}) => {

    const [search, setSearch] = useState("");

    const filteredConversations = useMemo(() => {

        const keyword = search.toLowerCase().trim();

        if (!keyword) {
            return conversations;
        }

        return conversations.filter((conversation) =>
            conversation.participantName
                ?.toLowerCase()
                .includes(keyword)
        );

    }, [conversations, search]);

    return (
        <aside className="conversation-list">

            <div className="conversation-heading">

                <div>
                    <h2>Conversations</h2>
                    <span>
                        {conversations.length} conversations
                    </span>
                </div>

                <FiUsers aria-hidden="true" />

            </div>

            <label className="message-search">

                <FiSearch aria-hidden="true" />

                <input
                    value={search}
                    onChange={(event) =>
                        setSearch(event.target.value)
                    }
                    placeholder="Search conversations"
                    aria-label="Search conversations"
                />

            </label>

            <div className="conversation-items">

                {filteredConversations.length > 0 ? (

                    filteredConversations.map((conversation) => (

                        <ConversationItem
                            key={conversation.conversationId}
                            conversation={conversation}
                            isSelected={
                                conversation.conversationId === activeId
                            }
                            onSelect={onSelect}
                        />

                    ))

                ) : (

                    <p className="empty-conversations">
                        No conversations found.
                    </p>

                )}

            </div>

            <button
                type="button"
                className="mobile-compose-button"
                onClick={onNewConversation}
                aria-label="Start a new conversation"
                title="Start a new conversation"
            >
                <FiPlus />
            </button>

        </aside>
    );
};

export default ConversationList;