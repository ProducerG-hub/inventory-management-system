import { useMemo, useState } from "react";
import { FiEdit3, FiSearch, FiSend, FiUsers } from "react-icons/fi";

import "./Messages.css";

const initialConversations = [
    {
        id: 1,
        name: "Inventory team",
        initials: "IT",
        role: "Operations",
        color: "blue",
        unread: 2,
        messages: [
            { id: 1, text: "The weekly stock count is ready for review.", time: "09:24", mine: false },
            { id: 2, text: "Thanks. I will check the variances this morning.", time: "09:31", mine: true }
        ]
    },
    {
        id: 2,
        name: "Grace Mwakalinga",
        initials: "GM",
        role: "Store manager",
        color: "coral",
        unread: 0,
        messages: [{ id: 3, text: "Can we replenish the fast-moving items today?", time: "Yesterday", mine: false }]
    },
    {
        id: 3,
        name: "System administrators",
        initials: "SA",
        role: "Announcement channel",
        color: "green",
        unread: 0,
        messages: [{ id: 4, text: "Scheduled maintenance is complete.", time: "Mon", mine: false }]
    }
];

const Messages = () => {
    const [conversations, setConversations] = useState(initialConversations);
    const [activeId, setActiveId] = useState(initialConversations[0].id);
    const [search, setSearch] = useState("");
    const [draft, setDraft] = useState("");

    const activeConversation = conversations.find((conversation) => conversation.id === activeId);
    const filteredConversations = useMemo(
        () => conversations.filter((conversation) => conversation.name.toLowerCase().includes(search.toLowerCase())),
        [conversations, search]
    );

    const selectConversation = (conversation) => {
        setActiveId(conversation.id);
        setConversations((current) => current.map((item) => item.id === conversation.id ? { ...item, unread: 0 } : item));
    };

    const sendMessage = (event) => {
        event.preventDefault();
        if (!draft.trim()) return;

        setConversations((current) => current.map((conversation) => conversation.id === activeId ? {
            ...conversation,
            messages: [...conversation.messages, { id: Date.now(), text: draft.trim(), time: "Now", mine: true }]
        } : conversation));
        setDraft("");
    };

    return (
        <section className="messages-page">
            <header className="messages-header">
                <div>
                    <span className="eyebrow">Team communication</span>
                    <h1>Messages</h1>
                    <p>Keep stock, sales, and operations moving together.</p>
                </div>
                <button type="button" className="compose-button" aria-label="Start a new conversation" title="Start a new conversation">
                    <FiEdit3 />
                    <span>New message</span>
                </button>
            </header>

            <div className="messages-shell">
                <aside className="conversation-list">
                    <div className="conversation-heading">
                        <div><h2>Conversations</h2><span>{conversations.length} channels</span></div>
                        <FiUsers aria-hidden="true" />
                    </div>
                    <label className="message-search">
                        <FiSearch aria-hidden="true" />
                        <input value={search} onChange={(event) => setSearch(event.target.value)} placeholder="Search conversations" aria-label="Search conversations" />
                    </label>
                    <div className="conversation-items">
                        {filteredConversations.map((conversation) => (
                            <button key={conversation.id} type="button" className={`conversation-item ${conversation.id === activeId ? "selected" : ""}`} onClick={() => selectConversation(conversation)}>
                                <span className={`avatar ${conversation.color}`}>{conversation.initials}</span>
                                <span className="conversation-copy"><strong>{conversation.name}</strong><small>{conversation.messages[conversation.messages.length - 1].text}</small></span>
                                {conversation.unread > 0 && <span className="unread-count">{conversation.unread}</span>}
                            </button>
                        ))}
                    </div>
                </aside>

                {activeConversation && <div className="chat-panel">
                    <div className="chat-heading">
                        <span className={`avatar ${activeConversation.color}`}>{activeConversation.initials}</span>
                        <div><h2>{activeConversation.name}</h2><span>{activeConversation.role}</span></div>
                    </div>
                    <div className="chat-messages">
                        <div className="date-divider"><span>Today</span></div>
                        {activeConversation.messages.map((message) => (
                            <div key={message.id} className={`message-row ${message.mine ? "mine" : ""}`}>
                                <div className="message-bubble"><span>{message.text}</span><small>{message.time}</small></div>
                            </div>
                        ))}
                    </div>
                    <form className="message-composer" onSubmit={sendMessage}>
                        <input value={draft} onChange={(event) => setDraft(event.target.value)} placeholder={`Message ${activeConversation.name}`} aria-label={`Message ${activeConversation.name}`} />
                        <button type="submit" aria-label="Send message" title="Send message"><FiSend /></button>
                    </form>
                </div>}
            </div>
        </section>
    );
};

export default Messages;