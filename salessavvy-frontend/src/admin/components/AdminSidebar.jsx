import { NavLink } from "react-router-dom";
import "../css/adminSidebar.css";

export default function AdminSidebar() {
    return (
        <aside className="admin-sidebar">
            <h2 className="logo">SalesSavvy</h2>

            <nav>
                <NavLink to="/admin/dashboard">Dashboard</NavLink>
                <NavLink to="/admin/products">Products</NavLink>
                <NavLink to="/admin/categories">Categories</NavLink>
                <NavLink to="/admin/orders">Orders</NavLink>
                <NavLink to="/admin/users">Users</NavLink>
            </nav>
        </aside>
    );
}