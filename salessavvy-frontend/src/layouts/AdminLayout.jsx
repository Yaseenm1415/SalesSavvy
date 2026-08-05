import { useState } from "react";
import { Outlet } from "react-router-dom";
import AdminHeader from "../admin/components/AdminHeader";
import AdminSidebar from "../admin/components/AdminSidebar";
import "../admin/css/adminLayout.css";

export default function AdminLayout() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(prev => !prev);
    };

    return (
        <div className={`admin-layout ${isSidebarOpen ? "sidebar-open" : ""}`}>
            <AdminSidebar isSidebarOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
            {isSidebarOpen && <div className="sidebar-overlay" onClick={toggleSidebar}></div>}
            <div className="admin-main">
                <AdminHeader toggleSidebar={toggleSidebar} />
                <main className="admin-content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}