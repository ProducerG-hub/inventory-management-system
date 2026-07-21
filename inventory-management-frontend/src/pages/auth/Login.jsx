import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { FiEye, FiEyeOff } from "react-icons/fi";
import toast from "react-hot-toast";

import { useAuth } from "../../context/AuthContext";
import authService from "../../services/authService";
import logo from "../../assets/images/mlue_logo(white).png";
import {
    emailValidation,
    passwordValidation,
} from "../../utils/validators";

import "./Login.css";

const Login = () => {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [loading, setLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const onSubmit = async (data) => {
        try {
            setLoading(true);

            const response = await authService.login(data);

            login(response.user, response.token);

            toast.success(response.message);

            navigate("/dashboard", { replace: true });

        } catch (error) {
            toast.error(
                error?.response?.data?.message ||
                "Invalid email or password"
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">

            {/* Brand Section */}

            <div className="login-brand">

                <div className="brand-content">

                    <div className="brand-logo">
                        <img src={logo} alt="MLUE Technology Logo" 
                        style={{ width: "100px", height: "100px", objectFit: "contain" }}
                        />
                    </div>

                    <h2>
                        Inventory Management System
                    </h2>

                    <p>
                        Smart inventory control solution for managing
                        products, suppliers and stock efficiently.
                    </p>

                </div>

                <div className="brand-circle circle-one"></div>
                <div className="brand-circle circle-two"></div>

            </div>

            {/* Login Section */}

            <div className="login-container">

                <div className="login-card">

                    <h2>Welcome Back</h2>

                    <p>Login to access your dashboard.</p>

                    <form onSubmit={handleSubmit(onSubmit)}>

                        {/* Email */}

                        <div className="form-group">

                            <label>Email Address</label>

                            <input
                                type="email"
                                placeholder="Enter your email"
                                {...register("email", emailValidation)}
                            />

                            {errors.email && (
                                <p className="error-message">
                                    {errors.email.message}
                                </p>
                            )}

                        </div>

                        {/* Password */}

                        <div className="form-group">

                            <label>Password</label>

                            <div className="password-wrapper">

                                <input
                                    type={showPassword ? "text" : "password"}
                                    placeholder="Enter your password"
                                    {...register(
                                        "password",
                                        passwordValidation
                                    )}
                                />

                                <button
                                    type="button"
                                    className="password-toggle"
                                    onClick={() =>
                                        setShowPassword(!showPassword)
                                    }
                                >
                                    {showPassword ? (
                                        <FiEyeOff />
                                    ) : (
                                        <FiEye />
                                    )}
                                </button>

                            </div>

                            {errors.password && (
                                <p className="error-message">
                                    {errors.password.message}
                                </p>
                            )}

                        </div>

                        {/* Login Button */}

                        <button
                            type="submit"
                            className="login-btn"
                            disabled={loading}
                        >
                            {loading ? "Signing In..." : "Login"}
                        </button>

                    </form>

                </div>

            </div>

        </div>
    );
};

export default Login;