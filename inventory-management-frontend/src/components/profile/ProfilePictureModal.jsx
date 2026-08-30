import { useEffect, useState } from "react";
import {
    FiCamera,
    FiCheck,
    FiTrash2,
    FiUpload,
    FiX
} from "react-icons/fi";

import profileService from "../../services/profileService";

import toast from "react-hot-toast";

import "./ProfilePictureModal.css";


const ProfilePictureModal = ({
    profile,
    onClose,
    onUpdated
}) => {

    const [selectedFile, setSelectedFile] =
        useState(null);

    const [preview, setPreview] =
        useState(
            profile?.profilePicture
                ? `http://localhost:8080${profile.profilePicture}`
                : null
        );

    const [uploading, setUploading] =
        useState(false);


    // ==========================================
    // CLEANUP PREVIEW URL
    // ==========================================

    useEffect(() => {

        return () => {

            if (preview?.startsWith("blob:")) {

                URL.revokeObjectURL(preview);

            }

        };

    }, [preview]);


    // ==========================================
    // SELECT IMAGE
    // ==========================================

    const handleFileChange = (event) => {

        const file =
            event.target.files?.[0];

        if (!file) return;


        if (file.size > 1024 * 1024) {

            toast.error(
                "Profile picture must not exceed 1MB"
            );

            event.target.value = "";

            return;
        }


        const allowedTypes = [
            "image/jpeg",
            "image/png",
            "image/webp"
        ];


        if (!allowedTypes.includes(file.type)) {

            toast.error(
                "Only JPEG, PNG and WebP images are allowed"
            );

            event.target.value = "";

            return;
        }


        setSelectedFile(file);

        setPreview(
            URL.createObjectURL(file)
        );

        event.target.value = "";
    };


    // ==========================================
    // UPLOAD
    // ==========================================

    const handleUpload = async () => {

        if (!selectedFile) {

            toast.error(
                "Please select an image first"
            );

            return;
        }


        try {

            setUploading(true);


            const updatedProfile =
                await profileService
                    .uploadProfilePicture(
                        selectedFile
                    );


            onUpdated(updatedProfile);

            toast.success(
                "Profile picture updated successfully"
            );

            onClose();

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to upload profile picture"
            );

        } finally {

            setUploading(false);

        }
    };


    // ==========================================
    // REMOVE
    // ==========================================

    const handleRemove = async () => {

        const confirmed =
            window.confirm(
                "Are you sure you want to remove your profile picture?"
            );

        if (!confirmed) return;


        try {

            setUploading(true);


            await profileService
                .deleteProfilePicture();


            onUpdated({
                ...profile,
                profilePicture: null
            });


            toast.success(
                "Profile picture removed"
            );

            onClose();

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to remove profile picture"
            );

        } finally {

            setUploading(false);

        }
    };


    return (

        <div
            className="profile-picture-overlay"
            onMouseDown={(event) => {

                if (
                    event.target ===
                    event.currentTarget &&
                    !uploading
                ) {

                    onClose();

                }

            }}
        >

            <div className="profile-picture-modal">


                {/* HEADER */}

                <div className="profile-picture-header">

                    <div>

                        <h3>
                            Profile picture
                        </h3>

                        <p>
                            Upload a clear profile image.
                        </p>

                    </div>


                    <button
                        type="button"
                        className="picture-close-btn"
                        onClick={onClose}
                        disabled={uploading}
                    >
                        <FiX />
                    </button>

                </div>


                {/* PREVIEW */}

                <div className="profile-picture-preview-area">

                    {preview ? (

                        <img
                            src={preview}
                            alt="Profile preview"
                            className="profile-picture-preview"
                        />

                    ) : (

                        <div className="profile-picture-empty">

                            <FiCamera />

                            <span>
                                No profile picture
                            </span>

                        </div>

                    )}

                </div>


                {/* INFO */}

                <div className="profile-picture-info">

                    <strong>
                        JPG, PNG or WebP
                    </strong>

                    <span>
                        Maximum file size: 1MB
                    </span>

                </div>


                {/* ACTIONS */}

                <div className="profile-picture-actions">


                    <label
                        className="btn btn-outline"
                    >

                        <FiUpload />

                        Choose image

                        <input
                            type="file"
                            accept="image/jpeg,image/png,image/webp"
                            onChange={handleFileChange}
                            disabled={uploading}
                            hidden
                        />

                    </label>


                    {profile?.profilePicture && (

                        <button
                            type="button"
                            className="btn btn-danger"
                            onClick={handleRemove}
                            disabled={uploading}
                        >

                            <FiTrash2 />

                            Remove

                        </button>

                    )}


                    <button
                        type="button"
                        className="btn btn-primary"
                        onClick={handleUpload}
                        disabled={
                            !selectedFile ||
                            uploading
                        }
                    >

                        <FiCheck />

                        {uploading
                            ? "Saving..."
                            : "Save picture"}

                    </button>

                </div>

            </div>

        </div>

    );
};


export default ProfilePictureModal;