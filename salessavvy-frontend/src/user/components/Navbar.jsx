import { Link } from "react-router-dom";
import "../css/navbar.css";
import { useAuth } from "../../context/AuthContext";
import { logout } from "../services/authService";

export default function Navbar() {

    const { user, setUser} = useAuth();

    const handleLogout = async () => {

        try {
            const response = await logout();
            setUser(null);
            console.log(response);
        } catch (error) {
            console.log(error);
        }

    };
    return (
        <nav className="navbar">
            <div className="logo">
                <Link to="/">Sales<span>Savvy</span></Link>
            </div>

            <ul className="nav-links">
                <li>
                    <Link to="/"><span className="nav-link-text">Home</span></Link>
                </li>
                {!user && (
                    <>
                        <li>
                            <Link to="/login"><span className="nav-link-text">Login</span></Link>
                        </li>

                        <li>
                            <Link to="/register"><span className="nav-link-text">Register</span></Link>
                        </li>
                    </>
                )}

                {user && user.role === "CUSTOMER" && (
                    <>
                        <li>
                            <Link to="/cart"><span className="nav-link-text">Cart</span></Link>
                        </li>
                        <li>
                            <Link to="/orders"><span className="nav-link-text">Orders</span></Link>
                        </li>
                        <li>
                            <Link to="/profile"><span className="nav-link-text">Profile</span></Link>
                        </li>
                        <li>
                            <button onClick={handleLogout} className="btn-click-effect">
                                <span className="nav-link-text">Logout</span>
                            </button>
                        </li>
                    </>
                )}
            </ul>
        </nav>
    );
}