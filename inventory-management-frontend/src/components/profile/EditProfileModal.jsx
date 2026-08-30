import { useEffect, useState } from "react";
import { FiSave, FiX } from "react-icons/fi";

import profileService from "../../services/profileService";

import toast from "react-hot-toast";

import "./EditProfileModal.css";


const EditProfileModal = ({
    profile,
    onClose,
    onUpdated
}) => {

    const [fullName, setFullName] =
        useState(profile?.fullName || "");

    const [saving, setSaving] =
        useState(false);


    // ==========================================
    // SYNC PROFILE
    // ==========================================

    useEffect(() => {

        setFullName(
            profile?.fullName || ""
        );

    }, [profile]);


    // ==========================================
    // SUBMIT
    // ==========================================

    const handleSubmit = async (event) => {

        event.preventDefault();


        if (!fullName.trim()) {

            toast.error(
                "Full name is required"
            );

            return;
        }


        try {

            setSaving(true);


            const updatedProfile =
                await profileService
                    .updateMyProfile({
                        fullName: fullName.trim()
                    });


            onUpdated(updatedProfile);

            toast.success(
                "Profile updated successfully"
            );

            onClose();

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to update profile"
            );

        } finally {

            setSaving(false);

        }
    };


    return (

        <div
            className="profile-modal-overlay"
            onMouseDown={(event) => {

                if (
                    event.target ===
                    event.currentTarget
                ) {
                    onClose();
                }

            }}
        >

            <div className="profile-modal">


                {/* HEADER */}

                <div className="profile-modal-header">

                    <div>

                        <h3>
                            Edit profile
                        </h3>

                        <p>
                            Update your personal information.
                        </p>

                    </div>


                    <button
                        type="button"
                        className="modal-close-btn"
                        onClick={onClose}
                        disabled={saving}
                    >

                        <FiX />

                    </button>

                </div>


                {/* FORM */}

                <form
                    className="profile-modal-form"
                    onSubmit={handleSubmit}
                >

                    <div className="form-group">

                        <label htmlFor="fullName">
                            Full name
                        </label>

                        <input
                            id="fullName"
                            type="text"
                            value={fullName}
                            onChange={(event) =>
                                setFullName(
                                    event.target.value
                                )
                            }
                            placeholder="Enter your full name"
                            maxLength={100}
                            disabled={saving}
                            autoFocus
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Email
                        </label>

                        <input
                            type="email"
                            value={
                                profile?.email || ""
                            }
                            disabled
                        />

                        <small>
                            Email address cannot be changed
                            from profile settings.
                        </small>

                    </div>


                    <div className="profile-modal-actions">

                        <button
                            type="button"
                            className="btn btn-outline"
                            onClick={onClose}
                            disabled={saving}
                        >
                            <FiX />
                            Cancel
                        </button>


                        <button
                            type="submit"
                            className="btn btn-primary"
                            disabled={saving}
                        >

                            <FiSave />

                            {saving
                                ? "Saving..."
                                : "Save changes"}

                        </button>

                    </div>

                </form>

            </div>

        </div>

    );
};


export default EditProfileModal;