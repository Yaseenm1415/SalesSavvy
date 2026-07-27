import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { adminLogin } from "../services/adminAuthService";
import "../css/adminLogin.css";

export default function AdminLogin() {
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[error, setError] = useState(null);
    const{ setUser } = useAuth();
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            const result = await adminLogin({
                username,
                password
            });
            
            if (result.role !== "ADMIN") {
                setError("You are not authorized.");
                return;
            }

            setUser({
                username: result.username,
                role: result.role
            });
            console.log(result);
            navigate("/admin/dashboard");
        } catch (error) {
            console.log(error);
            setError("Invalid username or password");
           
        }
       
    };


    return (
        <div className="auth-page-container">
            <div className="auth-card">
                <h1 className="auth-title">SalesSavvy</h1>
                <p className="auth-subtitle">Admin Login</p>
                {error && <p className="error-message">{error}</p>}
                
                <form className="login-form" onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Username</label>
                        <input 
                            type="text" 
                            className="form-input-standard" 
                            placeholder="Enter username" 
                            value={username} 
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input 
                            type="password" 
                            className="form-input-standard" 
                            placeholder="Enter password" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button className="btn-primary-standard auth-btn" type="submit">Login</button>
                </form>
            </div>
        </div>
    );
}