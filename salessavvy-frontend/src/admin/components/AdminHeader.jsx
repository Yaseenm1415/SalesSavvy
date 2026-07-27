import "../css/adminHeader.css";
export default function AdminHeader() {
return (
    <header className="admin-header">
        <h3>Admin Panel</h3>

        <button className="admin-logout-btn">Logout</button>
    </header>
);
}