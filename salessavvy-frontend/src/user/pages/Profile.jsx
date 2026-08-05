import { useAuth } from "../../context/AuthContext";
import "../css/profile.css";

export default function Profile() {
    const { user } = useAuth();

    if (!user) {
        return <div className="loading-indicator">Loading user profile...</div>;
    }

    return (
        <div className="profile-container">
            <h1 className="page-title">My Profile</h1>
            
            <div className="profile-card">
                <div className="profile-avatar">
                    {user.username?.charAt(0).toUpperCase()}
                </div>

                <h2 className="profile-username">
                    {user.username}
                </h2>

                <div className="profile-info-row">
                    <span className="profile-label">Account Status</span>
                    <span className="profile-value-active">✓ Active</span>
                </div>

                <div className="profile-info-row">
                    <span className="profile-label">Role</span>
                    <span className="status-badge placed profile-badge">
                        {user.role}
                    </span>
                </div>
            </div>
        </div>
    );
}