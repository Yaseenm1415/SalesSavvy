import { useAuth } from "../../context/AuthContext";

export default function Profile() {
    const { user } = useAuth();

    if (!user) {
        return <div className="loading-indicator">Loading user profile...</div>;
    }

    return (
        <div className="profile-container" style={{ maxWidth: "600px", margin: "0 auto" }}>
            <h1 className="page-title">My Profile</h1>
            
            <div className="profile-card" style={{
                background: "var(--bg-card)",
                border: "1px solid var(--border-color)",
                borderRadius: "var(--radius-lg)",
                padding: "40px",
                textAlign: "center",
                boxShadow: "var(--shadow-sm)"
            }}>
                <div className="profile-avatar" style={{
                    width: "80px",
                    height: "80px",
                    borderRadius: "var(--radius-full)",
                    background: "var(--primary-light)",
                    color: "var(--primary)",
                    fontSize: "32px",
                    fontWeight: "800",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    margin: "0 auto 24px"
                }}>
                    {user.username?.charAt(0).toUpperCase()}
                </div>

                <h2 style={{ fontSize: "20px", fontWeight: "700", marginBottom: "30px", color: "var(--text-main)" }}>
                    {user.username}
                </h2>

                <div style={{
                    display: "flex",
                    justifyContent: "space-between",
                    padding: "16px 0",
                    borderBottom: "1px solid var(--border-color)",
                    fontSize: "14px",
                    textAlign: "left"
                }}>
                    <span style={{ color: "var(--text-muted)", fontWeight: "600" }}>Account Status</span>
                    <span style={{ color: "var(--success)", fontWeight: "700" }}>✓ Active</span>
                </div>

                <div style={{
                    display: "flex",
                    justifyContent: "space-between",
                    padding: "16px 0",
                    fontSize: "14px",
                    textAlign: "left"
                }}>
                    <span style={{ color: "var(--text-muted)", fontWeight: "600" }}>Role</span>
                    <span className="status-badge placed" style={{ padding: "4px 12px", fontSize: "11px", fontWeight: "700" }}>
                        {user.role}
                    </span>
                </div>
            </div>
        </div>
    );
}