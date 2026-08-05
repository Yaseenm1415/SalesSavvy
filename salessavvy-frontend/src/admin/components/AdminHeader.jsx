import { useAuth } from "../../context/AuthContext";
import { logout } from "../../user/services/authService";
import "../css/adminHeader.css";
export default function AdminHeader({ toggleSidebar }) {
    const { setUser } = useAuth();
    const handleLogout = async () => {

        try {
            const response = await logout();
            setUser(null);
            console.log(response);
        } catch (error) {
            console.log(error);
        }
    }
return (
    <header className="admin-header">
        <div className="header-left">
            <button className="sidebar-toggle-btn btn-click-effect" onClick={toggleSidebar} aria-label="Toggle Sidebar">
                <span className="toggle-icon"></span>
            </button>
            <h3>Admin Panel</h3>
        </div>

        <button className="admin-logout-btn btn-click-effect" onClick={handleLogout}>Logout</button>
    </header>
);
}