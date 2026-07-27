import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { login } from "../services/authService";
import { useAuth } from "../../context/AuthContext";
import "../css/login.css";

export default function Login() {
    const[username, setUsername] = useState("");
    const[password, setPassword] = useState("");
    const[error, setError] = useState(null);
    const{ setUser } = useAuth();
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const result = await login({
                username,
                password
            });

            setUser({
                username: result.username,
                role: result.role
            });
            console.log(result);
            navigate("/");
        } catch (error) {
            console.log(error);
            setError("Invalid username or password");
           
        }
       
    };


    return (
        <div className="auth-page-container">
            <div className="auth-card">
                <h1 className="auth-title">SalesSavvy</h1>
                <p className="auth-subtitle">Login to continue</p>
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