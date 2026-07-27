import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

import { register } from "../services/authService"
import "../css/register.css";

export default function Register() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        try {
            const response = await register({username, email, password})
            console.log(response);
            navigate("/login");
        } catch (error) {
            console.log(error)
            setError("Registration failed");
        }
    };
    return (
        <div className="auth-page-container">
            <div className="auth-card">
                <form className="register-form" onSubmit={handleRegister}>
                    <h2 className="auth-title">Create Account</h2>

                    {error && <p className="error-message">{error}</p>}

                    <div className="form-group">
                        <label>Username</label>
                        <input
                            type="text"
                            className="form-input-standard"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter username"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            className="form-input-standard"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter email"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            className="form-input-standard"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter password"
                            required
                        />
                    </div>

                    <button type="submit" className="btn-primary-standard auth-btn">
                        Register
                    </button>

                    <p className="login-link">
                        Already have an account?
                        <Link to="/login"> Login</Link>
                    </p>
                </form>
            </div>
        </div>
    )
}