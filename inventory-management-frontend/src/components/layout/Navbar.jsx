import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useAuth } from "../../context/AuthContext"; 
import { FiUser, FiLogOut, FiMoreVertical, FiMessageCircle } from "react-icons/fi";
import { Client } from "@stomp/stompjs";
import navigation from "../../config/constants/navigation";
import ROUTES from "../../config/constants/routes";
import messageService from "../../services/messageService";
import storage from "../../utils/authStorage";

const Navbar = ({ onMenuClick, isSidebarOpen = false }) => {
    const [isProfileOpen, setIsProfileOpen] = useState(false);
    const [unreadCount, setUnreadCount] = useState(0);
    
    
    const { user, logout } = useAuth(); 
    const navigate = useNavigate();
    const location = useLocation();

    const token =
        storage.getToken();

    const currentPageTitle =
        navigation.find((item) => item.path === location.pathname)?.label ||
        (location.pathname === `/${ROUTES.MESSAGES}` ? "Messages" : "Mlue POS System");

    const toggleProfile = () => {
        setIsProfileOpen(!isProfileOpen);
    };

    
    const handleLogout = () => {
        logout(); 
        navigate("/login", { replace: true }); 
    };

    
    const getInitials = (fullName) => {
        if (!fullName) return "U"; 
        
        const nameArray = fullName.split(" ");
        if (nameArray.length >= 2) {
           
            return (nameArray[0][0] + nameArray[1][0]).toUpperCase();
        }
        
        return fullName.substring(0, 2).toUpperCase();
    };


    const loadUnreadCount = useCallback(async () => {

        try {

            const conversations =
                await messageService.getConversations();

            const totalUnread = conversations.reduce(
                (sum, conversation) =>
                    sum + Number(conversation.unreadCount || 0),
                0
            );

            setUnreadCount(totalUnread);

        } catch  {
            console.error("Error loading unread count:");
        }

    }, []);


    useEffect(() => {

        let isMounted = true;

        const safeLoadUnreadCount = async () => {

            await loadUnreadCount();

            if (!isMounted) {
                return;
            }

        };

        void safeLoadUnreadCount();

        const intervalId = setInterval(() => {
            void safeLoadUnreadCount();
        }, 15000);

        const handleWindowFocus = () => {
            void safeLoadUnreadCount();
        };

        window.addEventListener("focus", handleWindowFocus);

        return () => {

            isMounted = false;

            clearInterval(intervalId);

            window.removeEventListener("focus", handleWindowFocus);

        };

    }, [location.pathname, loadUnreadCount]);


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

                client.subscribe(
                    "/user/queue/messages",
                    () => {
                        void loadUnreadCount();
                    },
                    {
                        id: "navbar-unread-subscription"
                    }
                );
            },
            onStompError: () => {},
            onWebSocketError: () => {}
        });

        client.activate();

        return () => {
            if (client.active) {
                client.deactivate();
            }
        };

    }, [token, loadUnreadCount]);

    return (
        <header className="navbar-custom">
            <div className="navbar-left">
                <button
                    type="button"
                    className={`sidebar-toggle ${isSidebarOpen ? "active" : ""}`}
                    onClick={onMenuClick}
                    aria-label={isSidebarOpen ? "Close menu" : "Open menu"}
                    aria-expanded={isSidebarOpen}
                >
                    <FiMoreVertical />
                </button>

                <h5 className="navbar-title">{currentPageTitle}</h5>
            </div>
            
            <div className="navbar-right">
                <button
                    type="button"
                    className="navbar-action messages-link"
                    onClick={() => navigate(`/${ROUTES.MESSAGES}`)}
                    aria-label="Open messages"
                    title="Messages"
                >
                    <FiMessageCircle />
                    {unreadCount > 0 && (
                        <span className="message-badge">
                            {unreadCount > 99
                                ? "99+"
                                : unreadCount}
                        </span>
                    )}
                </button>

                <div className="profile-dropdown-wrapper">
                    
                    <div 
                        className="user-profile-circle" 
                        onClick={toggleProfile} 
                        title="User Profile"
                    >
                        
                        <span>{getInitials(user?.fullName)}</span>
                    </div>

                    {isProfileOpen && (
                        <div className="profile-popup-card">
                            <div className="popup-header">
                                
                                <p className="user-name">{user?.fullName || "Unknown User"}</p>
                                <p className="user-role">{user?.role || "No Role"}</p>
                            </div>
                            
                            <div className="popup-menu">
                                <div className="menu-item" onClick={() => {
                                    setIsProfileOpen(false);
                                    navigate("/profile"); 
                                }}>
                                    <span><FiUser /></span> Profile
                                </div>
                                
                                
                                <div className="menu-item logout" onClick={handleLogout}>
                                    <span><FiLogOut /></span> Logout
                                </div>
                            </div>
                        </div>
                    )}

                </div>
            </div>
        </header>
    );
};

export default Navbar;