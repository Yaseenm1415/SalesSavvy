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
                    <Link to="/">Home</Link>
                </li>
                {!user && (
                    <>
                        <li>
                            <Link to="/login">Login</Link>
                        </li>

                        <li>
                            <Link to="/register">Register</Link>
                        </li>
                    </>
                )}

                {user && user.role === "CUSTOMER" && (
                    <>
                        <li>
                            <Link to="/cart">Cart</Link>
                        </li>
                        <li>
                            <Link to="/orders">Orders</Link>
                        </li>
                        <li>
                            <Link to="/profile">Profile</Link>
                        </li>
                        <li>
                            <button onClick={handleLogout}>Logout</button>
                        </li>
                    </>
                )}

                {user && user.role === "ADMIN" && (
                    <>
                        <li>
                            <Link to="/admin/dashboard">Dashboard</Link>
                        </li>
                        <li>
                            <Link to="/admin/products">Products</Link>
                        </li>
                        <li>
                            <Link to="/admin/categories">Categories</Link>
                        </li>
                        <li>
                            <Link to="/admin/orders">Orders</Link>
                        </li>
                        <li>
                            <button onClick={handleLogout}>Logout</button>
                        </li>
                    </>
                )}
            </ul>
        </nav>
    );
}