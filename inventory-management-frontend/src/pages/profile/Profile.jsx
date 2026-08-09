import { useMemo, useState } from "react";
import { FiBell, FiEdit3, FiLock, FiMoon, FiShield, FiUser, FiActivity } from "react-icons/fi";
import { useAuth } from "../../context/AuthContext";
import PageHeader from "../../components/layout/PageHeader";
import "./Profile.css";

const Profile = () => {
    const { user } = useAuth();

    const [preferences, setPreferences] = useState({
        emailNotifications: true,
        darkMode: false,
        twoFactor: true,
        autoSave: true
    });

    const initials = useMemo(() => {
        if (!user?.fullName) return "U";

        const nameParts = user.fullName.trim().split(" ");

        if (nameParts.length >= 2) {
            return `${nameParts[0][0]}${nameParts[1][0]}`.toUpperCase();
        }

        return user.fullName.slice(0, 2).toUpperCase();
    }, [user?.fullName]);

    const handleToggle = (key) => {
        setPreferences((prev) => ({ ...prev, [key]: !prev[key] }));
    };

    return (
        <div className="profile-page">
            <PageHeader
                title="My Profile"
                subtitle="Manage your account information, security preferences, and personal settings in one place."
            />

            <div className="profile-grid">
                <section className="profile-card profile-hero">
                    <div className="profile-avatar">{initials}</div>
                    <div className="profile-hero-content">
                        <h3>{user?.fullName || "User Profile"}</h3>
                        <p>{user?.role || "Staff"} • {user?.email || "No email provided"}</p>
                        <div className="profile-actions">
                            <button type="button" className="btn btn-primary">
                                <FiEdit3 /> Edit profile
                            </button>
                            <button type="button" className="btn btn-outline-white">
                                <FiLock /> Change password
                            </button>
                        </div>
                    </div>
                </section>

                <section className="profile-card">
                    <div className="card-heading">
                        <FiUser />
                        <h4>Personal information</h4>
                    </div>

                    <div className="info-list">
                        <div className="info-item">
                            <span>Full name</span>
                            <strong>{user?.fullName || "Not available"}</strong>
                        </div>
                        <div className="info-item">
                            <span>Role</span>
                            <strong>{user?.role || "Staff"}</strong>
                        </div>
                        <div className="info-item">
                            <span>Email</span>
                            <strong>{user?.email || "No email provided"}</strong>
                        </div>
                        <div className="info-item">
                            <span>Phone</span>
                            <strong>{user?.phone || "Not provided"}</strong>
                        </div>
                    </div>
                </section>

                <section className="profile-card">
                    <div className="card-heading">
                        <FiShield />
                        <h4>Security settings</h4>
                    </div>

                    <div className="setting-list">
                        <label className="setting-item">
                            <div>
                                <strong>Two-step verification</strong>
                                <p>Protect your account with an extra verification layer.</p>
                            </div>
                            <input
                                type="checkbox"
                                checked={preferences.twoFactor}
                                onChange={() => handleToggle("twoFactor")}
                            />
                        </label>

                        <label className="setting-item">
                            <div>
                                <strong>Session timeout</strong>
                                <p>Automatically sign out after a period of inactivity.</p>
                            </div>
                            <span className="pill">24hrs</span>
                        </label>
                    </div>
                </section>

                <section className="profile-card">
                    <div className="card-heading">
                        <FiBell />
                        <h4>Preferences</h4>
                    </div>

                    <div className="setting-list">
                        <label className="setting-item">
                            <div>
                                <strong>Email notifications</strong>
                                <p>Receive updates about sales and stock activity.</p>
                            </div>
                            <input
                                type="checkbox"
                                checked={preferences.emailNotifications}
                                onChange={() => handleToggle("emailNotifications")}
                            />
                        </label>

                        <label className="setting-item">
                            <div>
                                <strong>Dark mode</strong>
                                <p>Switch the interface to a darker visual theme.</p>
                            </div>
                            <input
                                type="checkbox"
                                checked={preferences.darkMode}
                                onChange={() => handleToggle("darkMode")}
                            />
                        </label>

                        <label className="setting-item">
                            <div>
                                <strong>Auto-save drafts</strong>
                                <p>Keep your changes safe while updating records.</p>
                            </div>
                            <input
                                type="checkbox"
                                checked={preferences.autoSave}
                                onChange={() => handleToggle("autoSave")}
                            />
                        </label>
                    </div>
                </section>

                <section className="profile-card">
                    <div className="card-heading">
                        <FiActivity />
                        <h4>Recent activity</h4>
                    </div>

                    <ul className="activity-list">
                        <li>
                            <span className="activity-dot" />
                            Updated inventory preferences today
                        </li>
                        <li>
                            <span className="activity-dot" />
                            Logged in from the dashboard
                        </li>
                        <li>
                            <span className="activity-dot" />
                            Reviewed sales reporting insights
                        </li>
                    </ul>
                </section>
            </div>
        </div>
    );
};

export default Profile;
