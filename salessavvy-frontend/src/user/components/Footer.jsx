import { Link } from "react-router-dom";
import "../css/footer.css";

export default function Footer() {
    return (
        <footer className="footer">
            <div className="footer-container">
                <div className="footer-column brand-column">
                    <div className="footer-logo">
                        <Link to="/">Sales<span>Savvy</span></Link>
                    </div>
                    <p className="footer-desc">
                        Your premium destination for state-of-the-art smartphones, laptops, audio systems, and gaming accessories.
                    </p>
                </div>
                
                <div className="footer-column links-column">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/cart">Cart</Link></li>
                        <li><Link to="/orders">My Orders</Link></li>
                        <li><Link to="/profile">Profile</Link></li>
                    </ul>
                </div>
                
                <div className="footer-column contact-column">
                    <h3>Contact Us</h3>
                    <p>📧 support@salessavvy.com</p>
                    <p>📞 +91 98765 43210</p>
                    <p>📍 Tech Park, Bangalore, India</p>
                </div>
            </div>
            
            <div className="footer-bottom">
                <p>&copy; {new Date().getFullYear()} SalesSavvy. All rights reserved.</p>
            </div>
        </footer>
    );
}
