import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

import "./assets/styles/global.css";

import { AuthProvider } from "./context/AuthContext";
import { Toaster } from "react-hot-toast";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <AuthProvider>
            <App />
            <Toaster
    position="top-right"
    reverseOrder={false}
    gutter={12}
    toastOptions={{
        duration: 4000,

        style: {
            background: "#FFFFFF",
            color: "#1F2937",
            borderRadius: "12px",
            padding: "16px",
            boxShadow: "0 8px 24px rgba(0,0,0,.12)",
            fontFamily: "Poppins, sans-serif"
        },

        success: {
            iconTheme: {
                primary: "#22C55E",
                secondary: "#FFFFFF"
            }
        },

        error: {
            iconTheme: {
                primary: "#EF4444",
                secondary: "#FFFFFF"
            }
        }
    }}
/>
        </AuthProvider>
    </React.StrictMode>
);