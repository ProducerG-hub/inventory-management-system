import { useEffect, useState } from "react";
import {
    FiSearch,
    FiX,
    FiMessageCircle
} from "react-icons/fi";

import userService from "../../services/userService";
import storage from "../../utils/authStorage";

const NewConversationModal = ({
    isOpen,
    onClose,
    onConversationCreated
}) => {

    const [users, setUsers] = useState([]);
    const [search, setSearch] = useState("");

    const [loading, setLoading] = useState(false);
    const [creating, setCreating] = useState(false);

    const [error, setError] = useState("");



    // ==========================
    // LOAD ACTIVE USERS
    // ==========================

    useEffect(() => {

        if (!isOpen) {
            return;
        }

        const loadUsers = async () => {

    try {

        setLoading(true);
        setError("");
    const response = await userService.getActiveUsers({});

        const currentUserId =
            storage.getUser()?.userId;

        const usersData =
            Array.isArray(response)
                ? response
                : response.content || [];

        const activeUsers =
            usersData.filter(
                (user) =>
                    user.userId !== currentUserId
            );

        setUsers(activeUsers);

    } catch (error) {

        console.error(
            "Failed to load users:",
            error
        );

        setError(
            "Failed to load users. Please try again."
        );

    } finally {

        setLoading(false);

    }
};

        loadUsers();

    }, [isOpen]);



    // ==========================
    // FILTER USERS
    // ==========================

    const filteredUsers = users.filter((user) => {

        const keyword =
            search.toLowerCase().trim();

        if (!keyword) {
            return true;
        }

        return (
            user.fullName
                ?.toLowerCase()
                .includes(keyword)
            ||
            user.email
                ?.toLowerCase()
                .includes(keyword)
        );

    });



    // ==========================
    // START CONVERSATION
    // ==========================

    const handleStartConversation = async (user) => {

        try {

            setCreating(true);
            setError("");

            const conversation =
                await onConversationCreated(
                    user.userId
                );

            if (conversation) {

                setSearch("");

                onClose();

            }

        } catch (error) {

            console.error(
                "Failed to create conversation:",
                error
            );

            setError(
                "Failed to start conversation. Please try again."
            );

        } finally {

            setCreating(false);

        }

    };



    // ==========================
    // CLOSE MODAL
    // ==========================

    const handleClose = () => {

        if (creating) {
            return;
        }

        setSearch("");
        setError("");

        onClose();

    };



    // ==========================
    // DO NOT RENDER
    // ==========================

    if (!isOpen) {
        return null;
    }



    return (

        <div
            className="conversation-modal-overlay"
            onClick={handleClose}
        >

            <div
                className="conversation-modal"
                onClick={(event) =>
                    event.stopPropagation()
                }
            >


                {/* ==========================
                    HEADER
                ========================== */}

                <div className="conversation-modal-header">

                    <div>

                        <h2>
                            New message
                        </h2>

                        <p>
                            Select a user to start
                            a conversation.
                        </p>

                    </div>


                    <button
                        type="button"
                        className="modal-close-button"
                        onClick={handleClose}
                        disabled={creating}
                        aria-label="Close"
                        title="Close"
                    >
                        <FiX />
                    </button>

                </div>



                {/* ==========================
                    SEARCH
                ========================== */}

                <label
                    className="conversation-user-search"
                >

                    <FiSearch
                        aria-hidden="true"
                    />

                    <input
                        type="text"
                        value={search}
                        onChange={(event) =>
                            setSearch(
                                event.target.value
                            )
                        }
                        placeholder="Search users..."
                        aria-label="Search users"
                        autoFocus
                        disabled={creating}
                    />

                </label>



                {/* ==========================
                    ERROR
                ========================== */}

                {error && (

                    <div
                        className="conversation-modal-error"
                    >
                        {error}
                    </div>

                )}



                {/* ==========================
                    USER LIST
                ========================== */}

                <div className="conversation-user-list">

                    {loading ? (

                        <div className="modal-state">
                            Loading users...
                        </div>

                    ) : filteredUsers.length === 0 ? (

                        <div className="modal-state">

                            {search.trim()
                                ? "No users found."
                                : "No active users available."
                            }

                        </div>

                    ) : (

                        filteredUsers.map((user) => {

                            const initials =
                                user.fullName
                                    ?.split(" ")
                                    .map(
                                        (name) =>
                                            name[0]
                                    )
                                    .join("")
                                    .slice(0, 2)
                                    .toUpperCase();


                            return (

                                <button
                                    key={user.userId}
                                    type="button"
                                    className="conversation-user-item"
                                    disabled={creating}
                                    onClick={() =>
                                        handleStartConversation(
                                            user
                                        )
                                    }
                                >

                                    {/* Avatar */}

                                    <span className="avatar blue">

                                        {initials}

                                    </span>



                                    {/* User Information */}

                                    <span className="conversation-user-info">

                                        <strong>
                                            {user.fullName}
                                        </strong>

                                        <small>
                                            {user.email}
                                        </small>

                                    </span>



                                    {/* Message Icon */}

                                    <FiMessageCircle
                                        aria-hidden="true"
                                    />

                                </button>

                            );

                        })

                    )}

                </div>

            </div>

        </div>

    );

};

export default NewConversationModal;