import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext"; 
import { FiUser, FiLogOut } from "react-icons/fi";

const Navbar = () => {
    const [isProfileOpen, setIsProfileOpen] = useState(false);
    
    
    const { user, logout } = useAuth(); 
    const navigate = useNavigate();

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

    return (
        <header className="navbar-custom">
            <div className="navbar-left">
                <h5 className="navbar-title">Mlue POS System</h5>
            </div>
            
            <div className="navbar-right">
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