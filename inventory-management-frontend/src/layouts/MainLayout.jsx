import { useState } from "react";
import { Outlet } from "react-router-dom";

import Navbar from "../components/layout/Navbar";
import Sidebar from "../components/layout/Sidebar";
import Footer from "../components/layout/Footer";

const MainLayout = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const closeSidebar = () => {
        setIsSidebarOpen(false);
    };

    const toggleSidebar = () => {
        setIsSidebarOpen((current) => !current);
    };

    return (
        <div className={`app-layout ${isSidebarOpen ? "sidebar-open" : ""}`}>

            <Sidebar isOpen={isSidebarOpen} onNavigate={closeSidebar} />

            {isSidebarOpen && (
                <button
                    type="button"
                    className="sidebar-overlay"
                    aria-label="Close sidebar"
                    onClick={closeSidebar}
                />
            )}

            <div className="main-wrapper">

                <Navbar onMenuClick={toggleSidebar} isSidebarOpen={isSidebarOpen} />

                <main className="content-area">
                    <Outlet />
                </main>

                <Footer />

            </div>

        </div>
    );
};

export default MainLayout;