import { NavLink } from "react-router-dom";
import "../css/adminSidebar.css";

export default function AdminSidebar({ isSidebarOpen, toggleSidebar }) {
    const handleLinkClick = () => {
        if (window.innerWidth <= 900) {
            toggleSidebar();
        }
    };

    return (
        <aside className={`admin-sidebar ${isSidebarOpen ? "open" : ""}`}>
            <div className="sidebar-header">
                <h2 className="logo">SalesSavvy</h2>
                <button className="sidebar-close-btn btn-click-effect" onClick={toggleSidebar} aria-label="Close Sidebar">&times;</button>
            </div>

            <nav>
                <NavLink to="/admin/dashboard" onClick={handleLinkClick}>Dashboard</NavLink>
                <NavLink to="/admin/products" onClick={handleLinkClick}>Products</NavLink>
                <NavLink to="/admin/categories" onClick={handleLinkClick}>Categories</NavLink>
                <NavLink to="/admin/orders" onClick={handleLinkClick}>Orders</NavLink>
            </nav>
        </aside>
    );
}