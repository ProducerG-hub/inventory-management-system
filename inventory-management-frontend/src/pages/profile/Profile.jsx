import { useEffect, useMemo, useState } from "react";

import {
    FiCamera,
    FiEdit3,
    FiLock,
    FiMail,
    FiShield,
    FiUser
} from "react-icons/fi";

import { useAuth } from "../../context/AuthContext";

import PageHeader from "../../components/layout/PageHeader";

import profileService from "../../services/profileService";

import EditProfileModal
    from "../../components/profile/EditProfileModal";

import ChangePasswordModal
    from "../../components/profile/ChangePasswordModal";

import ProfilePictureModal
    from "../../components/profile/ProfilePictureModal";

import toast from "react-hot-toast";

import "./Profile.css";


const Profile = () => {

    const { user, updateUser } = useAuth();


    // ==========================================
    // STATE
    // ==========================================

    const [profile, setProfile] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    const [showEditModal, setShowEditModal] =
        useState(false);

    const [showPasswordModal, setShowPasswordModal] =
        useState(false);

    const [showPictureModal, setShowPictureModal] =
        useState(false);


    // ==========================================
    // FETCH PROFILE
    // ==========================================

    const fetchProfile = async () => {

        try {

            setLoading(true);

            const data =
                await profileService.getMyProfile();

            setProfile(data);

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to load profile"
            );

        } finally {

            setLoading(false);

        }
    };


    useEffect(() => {

        fetchProfile();

    }, []);


    // ==========================================
    // PROFILE DATA
    // ==========================================

    const fullName =
        profile?.fullName ||
        user?.fullName ||
        "User Profile";


    const email =
        profile?.email ||
        user?.email ||
        "No email provided";


    const role =
        profile?.role ||
        user?.role ||
        "STAFF";


    const isActive =
        profile?.isActive ?? true;


    // ==========================================
    // INITIALS
    // ==========================================

    const initials = useMemo(() => {

        if (!fullName) {
            return "U";
        }


        const nameParts =
            fullName.trim().split(/\s+/);


        if (nameParts.length >= 2) {

            return (
                `${nameParts[0][0]}${nameParts[1][0]}`
            ).toUpperCase();

        }


        return fullName
            .slice(0, 2)
            .toUpperCase();

    }, [fullName]);


    // ==========================================
    // PROFILE PICTURE URL
    // ==========================================

    const profilePictureUrl =
        profile?.profilePicture
            ? `http://localhost:8080${profile.profilePicture}`
            : null;


    // ==========================================
    // LOADING STATE
    // ==========================================

    if (loading) {

        return (

            <div className="profile-page">

                <PageHeader
                    title="My Profile"
                    subtitle="Manage your account information and security."
                />

                <div className="profile-loading">

                    <div className="profile-loading-spinner" />

                    <span>
                        Loading profile...
                    </span>

                </div>

            </div>

        );

    }


    // ==========================================
    // PAGE
    // ==========================================

    return (

        <div className="profile-page">

            <div className="profile-grid">


                {/* ==================================
                    PROFILE HERO
                ================================== */}

                <section className="profile-card profile-hero">


                    <div className="profile-avatar-wrapper">

                        {profilePictureUrl ? (

                            <img
                                src={profilePictureUrl}
                                alt={`${fullName} profile`}
                                className="profile-avatar-image"
                            />

                        ) : (

                            <div
                                className="profile-avatar"
                                aria-label={`${fullName} initials`}
                            >
                                {initials}
                            </div>

                        )}


                        <button
                            type="button"
                            className="profile-camera-button"
                            onClick={() =>
                                setShowPictureModal(true)
                            }
                            title="Change profile picture"
                            aria-label="Change profile picture"
                        >

                            <FiCamera />

                        </button>

                    </div>


                    <div className="profile-hero-content">


                        <div className="profile-name-row">

                            <h3>
                                {fullName}
                            </h3>


                            <span className="profile-status-badge">

                                <span className="status-dot" />

                                {isActive
                                    ? "Active"
                                    : "Inactive"}

                            </span>

                        </div>


                        <p className="profile-meta">

                            <span className="profile-role">
                                {role}
                            </span>

                            <span className="profile-separator">
                                •
                            </span>

                            <span>
                                {email}
                            </span>

                        </p>


                        <div className="profile-actions">

                            <button
                                type="button"
                                className="btn btn-primary"
                                onClick={() =>
                                    setShowEditModal(true)
                                }
                            >

                                <FiEdit3 />

                                Edit profile

                            </button>


                            <button
                                type="button"
                                className="btn btn-outline-white"
                                onClick={() =>
                                    setShowPasswordModal(true)
                                }
                            >

                                <FiLock />

                                Change password

                            </button>

                        </div>

                    </div>

                </section>


                {/* ==================================
                    PERSONAL INFORMATION
                ================================== */}

                <section className="profile-card">


                    <div className="card-heading">

                        <div className="card-heading-icon">
                            <FiUser />
                        </div>


                        <div>

                            <h4>
                                Personal information
                            </h4>

                            <p>
                                Your account information
                            </p>

                        </div>

                    </div>


                    <div className="info-list">


                        <div className="info-item">

                            <span>
                                Full name
                            </span>

                            <strong>
                                {fullName}
                            </strong>

                        </div>


                        <div className="info-item">

                            <span>
                                Email address
                            </span>

                            <strong>
                                {email}
                            </strong>

                        </div>


                        <div className="info-item">

                            <span>
                                Role
                            </span>

                            <strong className="role-value">
                                {role}
                            </strong>

                        </div>


                        <div className="info-item">

                            <span>
                                Account status
                            </span>

                            <strong
                                className={
                                    isActive
                                        ? "status-value"
                                        : "status-value inactive"
                                }
                            >
                                {isActive
                                    ? "Active"
                                    : "Inactive"}
                            </strong>

                        </div>


                    </div>

                </section>


                {/* ==================================
                    SECURITY
                ================================== */}

                <section className="profile-card">


                    <div className="card-heading">

                        <div className="card-heading-icon">
                            <FiShield />
                        </div>


                        <div>

                            <h4>
                                Security
                            </h4>

                            <p>
                                Manage your account security
                            </p>

                        </div>

                    </div>


                    <div className="security-list">


                        {/* PASSWORD */}

                        <div className="security-item">

                            <div className="security-item-icon">
                                <FiLock />
                            </div>


                            <div className="security-item-content">

                                <strong>
                                    Password
                                </strong>

                                <p>
                                    Keep your account secure
                                    with a strong password.
                                </p>

                            </div>


                            <button
                                type="button"
                                className="btn btn-outline"
                                onClick={() =>
                                    setShowPasswordModal(true)
                                }
                            >

                                <FiLock />

                                Change

                            </button>

                        </div>


                        {/* EMAIL */}

                        <div className="security-item">

                            <div className="security-item-icon">
                                <FiMail />
                            </div>


                            <div className="security-item-content">

                                <strong>
                                    Email address
                                </strong>

                                <p>
                                    {email}
                                </p>

                            </div>


                            <span className="security-status">
                                Verified
                            </span>

                        </div>


                    </div>

                </section>


            </div>


            {/* ==================================
                EDIT PROFILE MODAL
            ================================== */}

            {showEditModal && profile && (

                <EditProfileModal

                    profile={profile}

                    onClose={() =>
                        setShowEditModal(false)
                    }

                    onUpdated={(updatedProfile) => {
                        setProfile(updatedProfile);
                        updateUser(updatedProfile);
                    }}

                />

            )}


            {/* ==================================
                CHANGE PASSWORD MODAL
            ================================== */}

            {showPasswordModal && (

                <ChangePasswordModal

                    onClose={() =>
                        setShowPasswordModal(false)
                    }

                />

            )}


            {/* ==================================
                PROFILE PICTURE MODAL
            ================================== */}

            {showPictureModal && profile && (

                <ProfilePictureModal

                    profile={profile}

                    onClose={() =>
                        setShowPictureModal(false)
                    }

                    onUpdated={(updatedProfile) => {
                        setProfile(updatedProfile);
                        updateUser(updatedProfile);
                    }}

                />

            )}

        </div>

    );
};


export default Profile;
