import { useEffect, useState } from "react";
import { FiEdit3 } from "react-icons/fi";

import ConversationList from "../../components/messages/ConversationList";
import ChatWindow from "../../components/messages/ChatWindow";
import NewConversationModal from "../../components/messages/NewConversationModal";

import messageService from "../../services/messageService";
import storage from "../../utils/authStorage";

import "./Messages.css";


const MessagesPage = () => {

    // ==========================
    // STATE
    // ==========================

    const [conversations, setConversations] = useState([]);

    const [activeConversation, setActiveConversation] =
        useState(null);

    const [messages, setMessages] = useState([]);

    const [loadingConversations, setLoadingConversations] =
        useState(true);

    const [loadingMessages, setLoadingMessages] =
        useState(false);

    const [isMobileChatOpen, setIsMobileChatOpen] =
        useState(false);

    const [isNewConversationOpen, setIsNewConversationOpen] =
        useState(false);


    // ==========================
    // CURRENT USER
    // ==========================

    const currentUserId =
        storage.getUser()?.userId;



    // ==========================
    // LOAD CONVERSATIONS
    // ==========================

    useEffect(() => {

        const loadConversations = async () => {

            try {

                setLoadingConversations(true);

                const data =
                    await messageService.getConversations();

                setConversations(data);

            } catch (error) {

                console.error(
                    "Failed to load conversations:",
                    error
                );

            } finally {

                setLoadingConversations(false);

            }

        };

        loadConversations();

    }, []);



    // ==========================
    // SELECT CONVERSATION
    // ==========================

    const selectConversation = async (
        conversation
    ) => {

        setActiveConversation(conversation);

        setIsMobileChatOpen(true);

        try {

            setLoadingMessages(true);

            const data =
                await messageService.getMessages(
                    conversation.conversationId
                );

            setMessages(data);

            await messageService.markAsRead(
                conversation.conversationId
            );

            setConversations((current) =>
                current.map((item) =>
                    item.conversationId ===
                    conversation.conversationId
                        ? {
                            ...item,
                            unreadCount: 0
                        }
                        : item
                )
            );

        } catch (error) {

            console.error(
                "Failed to load messages:",
                error
            );

        } finally {

            setLoadingMessages(false);

        }

    };



    // ==========================
    // SEND MESSAGE
    // ==========================

    const sendMessage = async (
        conversationId,
        messageContent
    ) => {

        try {

            const newMessage =
                await messageService.sendMessage(
                    conversationId,
                    messageContent
                );

            setMessages((current) => [
                ...current,
                newMessage
            ]);

            setConversations((current) =>
                current.map((conversation) =>
                    conversation.conversationId ===
                    conversationId
                        ? {
                            ...conversation,
                            lastMessage: newMessage,
                            updatedAt: newMessage.sentAt
                        }
                        : conversation
                )
            );

        } catch (error) {

            console.error(
                "Failed to send message:",
                error
            );

        }

    };



    // ==========================
    // CREATE NEW CONVERSATION
    // ==========================

    const handleConversationCreated = async (
        participantId
    ) => {

        try {

            const conversation =
                await messageService.createConversation(
                    participantId
                );

            setConversations((current) => {

                const exists = current.some(
                    (item) =>
                        item.conversationId ===
                        conversation.conversationId
                );

                if (exists) {

                    return current;

                }

                return [
                    conversation,
                    ...current
                ];

            });


            // Open conversation automatically

            await selectConversation(
                conversation
            );


            return conversation;

        } catch (error) {

            console.error(
                "Failed to create conversation:",
                error
            );

            throw error;

        }

    };



    // ==========================
    // CLOSE NEW CONVERSATION MODAL
    // ==========================

    const closeNewConversationModal = () => {

        setIsNewConversationOpen(false);

    };



    // ==========================
    // RENDER
    // ==========================

    return (

        <section className="messages-page">


            {/* ==========================
                PAGE HEADER
            ========================== */}

            <header className="messages-header">

                <div>

                    <span className="eyebrow">
                        Team communication
                    </span>

                    <h1>
                        Messages
                    </h1>

                    <p>
                        Keep stock, sales, and operations
                        moving together.
                    </p>

                </div>


                <button
                    type="button"
                    className="compose-button"
                    onClick={() =>
                        setIsNewConversationOpen(true)
                    }
                    aria-label="Start a new conversation"
                    title="Start a new conversation"
                >

                    <FiEdit3 />

                    <span>
                        New message
                    </span>

                </button>

            </header>



            {/* ==========================
                MESSAGES SHELL
            ========================== */}

            <div
                className={`messages-shell ${
                    isMobileChatOpen
                        ? "mobile-chat-open"
                        : ""
                }`}
            >


                {/* ==========================
                    CONVERSATION LIST
                ========================== */}

                {loadingConversations ? (

                    <div className="messages-loading">
                        Loading conversations...
                    </div>

                ) : (
                <ConversationList
                    conversations={conversations}
                    activeId={
                        activeConversation?.conversationId
                    }
                    onSelect={selectConversation}
                    onNewConversation={() =>
                        setIsNewConversationOpen(true)
                    }
                />

                )}



                {/* ==========================
                    CHAT WINDOW
                ========================== */}

                <ChatWindow
                    conversation={
                        activeConversation
                    }
                    messages={messages}
                    currentUserId={
                        currentUserId
                    }
                    onSend={sendMessage}
                    onBack={() =>
                        setIsMobileChatOpen(
                            false
                        )
                    }
                    loading={
                        loadingMessages
                    }
                />

            </div>



            {/* ==========================
                NEW CONVERSATION MODAL
            ========================== */}

            <NewConversationModal
                isOpen={
                    isNewConversationOpen
                }
                onClose={
                    closeNewConversationModal
                }
                onConversationCreated={
                    handleConversationCreated
                }
            />

        </section>

    );

};

export default MessagesPage;