import { useCallback, useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
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

    const [conversationSelectionVersion, setConversationSelectionVersion] =
        useState(0);

    const [isWebSocketConnected, setIsWebSocketConnected] =
        useState(false);

    const clientRef = useRef(null);

    const activeConversationIdRef = useRef(null);

    const clearedCutoffByConversationRef = useRef({});


    // ==========================
    // CURRENT USER
    // ==========================

    const currentUserId =
        storage.getUser()?.userId;

    const token =
        storage.getToken();


    useEffect(() => {

        activeConversationIdRef.current =
            activeConversation?.conversationId ?? null;

    }, [activeConversation]);


    const appendMessageIfMissing = (
        current,
        message
    ) => {

        const exists = current.some(
            (item) => item.messageId === message.messageId
        );

        if (exists) {
            return current;
        }

        return [
            ...current,
            message
        ];

    };


    const mergeMessagesById = (
        current,
        incoming
    ) => {

        const byId = new Map();

        current.forEach((item) => {
            byId.set(item.messageId, item);
        });

        incoming.forEach((item) => {
            byId.set(item.messageId, item);
        });

        return [...byId.values()].sort((a, b) =>
            new Date(a.sentAt || 0).getTime() -
            new Date(b.sentAt || 0).getTime()
        );

    };


    const filterMessagesByClearCutoff = useCallback((
        conversationId,
        messageList
    ) => {

        const cutoff =
            clearedCutoffByConversationRef.current[
                conversationId
            ];

        if (!cutoff) {
            return messageList;
        }

        const cutoffTime =
            new Date(cutoff).getTime();

        return messageList.filter((message) =>
            new Date(message.sentAt || 0).getTime() > cutoffTime
        );

    }, []);


    const sortByUpdatedAtDesc = (
        items
    ) => {

        return [...items].sort((a, b) => {

            const aTime =
                new Date(a.updatedAt || 0).getTime();

            const bTime =
                new Date(b.updatedAt || 0).getTime();

            return bTime - aTime;

        });

    };


    const loadConversations = useCallback(async () => {

        try {

            setLoadingConversations(true);

            const data =
                await messageService.getConversations();

            setConversations(data);

        } catch {
            void 0;
        } finally {

            setLoadingConversations(false);

        }

    }, []);



    // ==========================
    // LOAD CONVERSATIONS
    // ==========================

    useEffect(() => {

        const timerId = setTimeout(() => {
            void loadConversations();
        }, 0);

        return () => {
            clearTimeout(timerId);
        };

    }, [loadConversations]);


    useEffect(() => {

        if (!token) {
            return undefined;
        }

        const client = new Client({
            brokerURL: "ws://localhost:8080/api/ws",
            connectHeaders: {
                Authorization: `Bearer ${token}`
            },
            reconnectDelay: 5000,
            onConnect: () => {

                setIsWebSocketConnected(true);

                client.subscribe(
                    "/user/queue/messages",
                    async (frame) => {

                        let incomingMessage;

                        try {
                            incomingMessage = JSON.parse(frame.body);
                        } catch {
                            console.error(
                                "Failed to parse incoming message:"
                            );
                            return;
                        }

                        const incomingConversationId =
                            Number(incomingMessage.conversationId);

                        const isActiveConversation =
                            Number(activeConversationIdRef.current) ===
                            incomingConversationId;

                        if (isActiveConversation) {

                            const visibleMessages =
                                filterMessagesByClearCutoff(
                                    incomingConversationId,
                                    [incomingMessage]
                                );

                            if (!visibleMessages.length) {
                                return;
                            }

                            setMessages((current) =>
                                appendMessageIfMissing(
                                    current,
                                    visibleMessages[0]
                                )
                            );

                            try {
                                await messageService.markAsRead(
                                    incomingConversationId
                                );
                            } catch {
                                console.error(
                                    "Failed to mark messages as read for conversation:"
                                );
                            }

                        }

                        let conversationFound = false;

                        setConversations((current) => {

                            const updated = current.map((conversation) => {

                                if (
                                    Number(conversation.conversationId) !==
                                    incomingConversationId
                                ) {
                                    return conversation;
                                }

                                conversationFound = true;

                                const shouldIncreaseUnread =
                                    incomingMessage.senderId !== currentUserId &&
                                    !isActiveConversation;

                                return {
                                    ...conversation,
                                    lastMessage: incomingMessage,
                                    updatedAt: incomingMessage.sentAt,
                                    unreadCount: shouldIncreaseUnread
                                        ? (conversation.unreadCount || 0) + 1
                                        : 0
                                };

                            });

                            return sortByUpdatedAtDesc(updated);

                        });

                        if (!conversationFound) {
                            await loadConversations();
                        }

                    },
                    {
                        id: "messages-page-subscription"
                    }
                );
            },
            onDisconnect: () => {
                setIsWebSocketConnected(false);
            },
            onStompError: () => {},
            onWebSocketClose: () => {
                setIsWebSocketConnected(false);
            },
            onWebSocketError: () => {
                setIsWebSocketConnected(false);
            }
        });

        clientRef.current = client;

        client.activate();

        return () => {
            if (client.active) {
                client.deactivate();
            }
        };

    }, [token, currentUserId, loadConversations, filterMessagesByClearCutoff]);


    useEffect(() => {

        const activeConversationId =
            activeConversation?.conversationId;

        if (!activeConversationId) {
            return undefined;
        }

        const intervalId = setInterval(async () => {

            try {

                const latestMessages =
                    await messageService.getMessages(
                        activeConversationId
                    );

                const visibleMessages =
                    filterMessagesByClearCutoff(
                        activeConversationId,
                        latestMessages
                    );

                setMessages((current) =>
                    mergeMessagesById(
                        current,
                        visibleMessages
                    )
                );

                const hasUnreadIncoming =
                    visibleMessages.some((message) =>
                        message.senderId !== currentUserId &&
                        !message.isRead
                    );

                if (hasUnreadIncoming) {

                    await messageService.markAsRead(
                        activeConversationId
                    );

                    setConversations((current) =>
                        current.map((item) =>
                            item.conversationId === activeConversationId
                                ? {
                                    ...item,
                                    unreadCount: 0
                                }
                                : item
                        )
                    );

                }

            } catch {
                console.error(
                    "Error refreshing messages for conversation:"
                );
            }

        }, 3000);

        return () => {
            clearInterval(intervalId);
        };

    }, [activeConversation?.conversationId, currentUserId, filterMessagesByClearCutoff]);



    // ==========================
    // SELECT CONVERSATION
    // ==========================

    const selectConversation = async (
        conversation
    ) => {

        setConversationSelectionVersion((current) =>
            current + 1
        );

        setActiveConversation(conversation);

        setIsMobileChatOpen(true);

        try {

            setLoadingMessages(true);

            const data =
                await messageService.getMessages(
                    conversation.conversationId
                );

            setMessages(
                filterMessagesByClearCutoff(
                    conversation.conversationId,
                    data
                )
            );

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

        } catch {
            console.error(
                "Error loading messages for conversation:"
            );
        } finally {

            setLoadingMessages(false);

        }

    };


    const clearActiveConversationMessages = useCallback((
        conversationId
    ) => {

        const normalizedConversationId =
            Number(conversationId);

        clearedCutoffByConversationRef.current[
            normalizedConversationId
        ] = new Date().toISOString();

        if (
            Number(activeConversationIdRef.current) ===
            normalizedConversationId
        ) {
            setMessages([]);
        }

    }, []);



    // ==========================
    // SEND MESSAGE
    // ==========================

    const sendMessage = async (
        conversationId,
        messageContent
    ) => {

        try {

            const client = clientRef.current;

            if (client?.connected) {

                client.publish({
                    destination: "/app/messages",
                    body: JSON.stringify({
                        conversationId,
                        messageContent
                    })
                });

                return;

            }


            /*
             * Fallback keeps chat usable when websocket
             * is reconnecting or temporarily unavailable.
             */

            const newMessage = await messageService.sendMessage(
                conversationId,
                messageContent
            );

            setMessages((current) =>
                appendMessageIfMissing(
                    current,
                    newMessage
                )
            );

            setConversations((current) => {

                const updated = current.map((conversation) =>
                    conversation.conversationId === conversationId
                        ? {
                            ...conversation,
                            lastMessage: newMessage,
                            updatedAt: newMessage.sentAt,
                            unreadCount: 0
                        }
                        : conversation
                );

                return sortByUpdatedAtDesc(updated);

            });

        } catch {
            console.error(
                "Error sending message for conversation:"
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
            throw new Error(
                "Failed to create conversation: " +
                (error?.message || "Unknown error")
            );

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
                    key={
                        activeConversation?.conversationId || "empty-chat"
                    }
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
                    isWebSocketConnected={
                        isWebSocketConnected
                    }
                    forceScrollTrigger={
                        conversationSelectionVersion
                    }
                    onClearChat={
                        clearActiveConversationMessages
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