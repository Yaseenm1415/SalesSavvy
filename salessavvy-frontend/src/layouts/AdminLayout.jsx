import { Outlet } from "react-router-dom";
import AdminHeader from "../admin/components/AdminHeader";
import AdminSidebar from "../admin/components/AdminSidebar";
import "../admin/css/adminLayout.css";

export default function AdminLayout() {
    return (
        <div className="admin-layout">
            <AdminSidebar />
            <div className="admin-main">
                <AdminHeader />
                <main className="admin-content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}