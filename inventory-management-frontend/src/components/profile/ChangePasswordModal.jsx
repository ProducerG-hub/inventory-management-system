import { useState } from "react";
import { FiEye, FiEyeOff, FiLock, FiSave, FiX } from "react-icons/fi";

import profileService from "../../services/profileService";

import toast from "react-hot-toast";

import "./ChangePasswordModal.css";


const ChangePasswordModal = ({
    onClose
}) => {

    const [formData, setFormData] = useState({
        currentPassword: "",
        newPassword: "",
        confirmPassword: ""
    });

    const [showPasswords, setShowPasswords] =
        useState({
            current: false,
            new: false,
            confirm: false
        });

    const [saving, setSaving] =
        useState(false);


    // ==========================================
    // HANDLE INPUT
    // ==========================================

    const handleChange = (event) => {

        const { name, value } =
            event.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };


    // ==========================================
    // TOGGLE PASSWORD VISIBILITY
    // ==========================================

    const togglePassword = (field) => {

        setShowPasswords((prev) => ({
            ...prev,
            [field]: !prev[field]
        }));
    };


    // ==========================================
    // SUBMIT
    // ==========================================

    const handleSubmit = async (event) => {

        event.preventDefault();


        const {
            currentPassword,
            newPassword,
            confirmPassword
        } = formData;


        if (
            !currentPassword ||
            !newPassword ||
            !confirmPassword
        ) {

            toast.error(
                "Please fill in all password fields"
            );

            return;
        }


        if (newPassword.length < 8) {

            toast.error(
                "New password must be at least 8 characters"
            );

            return;
        }


        if (newPassword !== confirmPassword) {

            toast.error(
                "New passwords do not match"
            );

            return;
        }


        try {

            setSaving(true);


            await profileService.changePassword(
                formData
            );


            toast.success(
                "Password changed successfully"
            );


            setFormData({
                currentPassword: "",
                newPassword: "",
                confirmPassword: ""
            });


            onClose();

        } catch (error) {

            toast.error(
                error.response?.data?.message ||
                "Failed to change password"
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
                            Change password
                        </h3>

                        <p>
                            Update your password to keep
                            your account secure.
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


                    {/* CURRENT PASSWORD */}

                    <div className="form-group">

                        <label>
                            Current password
                        </label>

                        <div className="password-input-wrapper">

                            <input
                                type={
                                    showPasswords.current
                                        ? "text"
                                        : "password"
                                }
                                name="currentPassword"
                                value={
                                    formData.currentPassword
                                }
                                onChange={handleChange}
                                placeholder="Enter current password"
                                disabled={saving}
                            />

                            <button
                                type="button"
                                onClick={() =>
                                    togglePassword("current")
                                }
                                tabIndex="-1"
                            >
                                {showPasswords.current
                                    ? <FiEyeOff />
                                    : <FiEye />}
                            </button>

                        </div>

                    </div>


                    {/* NEW PASSWORD */}

                    <div className="form-group">

                        <label>
                            New password
                        </label>

                        <div className="password-input-wrapper">

                            <input
                                type={
                                    showPasswords.new
                                        ? "text"
                                        : "password"
                                }
                                name="newPassword"
                                value={
                                    formData.newPassword
                                }
                                onChange={handleChange}
                                placeholder="Enter new password"
                                disabled={saving}
                            />

                            <button
                                type="button"
                                onClick={() =>
                                    togglePassword("new")
                                }
                                tabIndex="-1"
                            >
                                {showPasswords.new
                                    ? <FiEyeOff />
                                    : <FiEye />}
                            </button>

                        </div>

                        <small>
                            Minimum 8 characters.
                        </small>

                    </div>


                    {/* CONFIRM PASSWORD */}

                    <div className="form-group">

                        <label>
                            Confirm new password
                        </label>

                        <div className="password-input-wrapper">

                            <input
                                type={
                                    showPasswords.confirm
                                        ? "text"
                                        : "password"
                                }
                                name="confirmPassword"
                                value={
                                    formData.confirmPassword
                                }
                                onChange={handleChange}
                                placeholder="Confirm new password"
                                disabled={saving}
                            />

                            <button
                                type="button"
                                onClick={() =>
                                    togglePassword("confirm")
                                }
                                tabIndex="-1"
                            >
                                {showPasswords.confirm
                                    ? <FiEyeOff />
                                    : <FiEye />}
                            </button>

                        </div>

                    </div>


                    {/* ACTIONS */}

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

                            <FiLock />

                            {saving
                                ? "Updating..."
                                : "Change password"}

                        </button>

                    </div>

                </form>

            </div>

        </div>

    );
};


export default ChangePasswordModal;