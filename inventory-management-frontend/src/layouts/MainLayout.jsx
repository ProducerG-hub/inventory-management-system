import { Outlet } from "react-router-dom";

import Navbar from "../components/layout/Navbar";
import Sidebar from "../components/layout/Sidebar";
import Footer from "../components/layout/Footer";

const MainLayout = () => {
    return (
        <div className="app-layout">

            <Sidebar />

            <div className="main-wrapper">

                <Navbar />

                <main className="content-area">
                    <Outlet />
                </main>

                <Footer />

            </div>

        </div>
    );
};

export default MainLayout;