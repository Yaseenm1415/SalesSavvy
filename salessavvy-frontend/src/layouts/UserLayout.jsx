import Navbar from "../user/components/Navbar";
import Footer from "../user/components/Footer";
import { Outlet } from "react-router-dom";

export default function UserLayout() {
    return (
        <div className="user-layout-container">
            <Navbar />
            <main className="main-content-wrapper">
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}