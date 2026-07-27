import { Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext"

export default function AdminRoute() {
    const { user } = useAuth();

    if (!user || user.role !== "ADMIN") {
        return <navigate to = "/admin/login" />;
    }

    return <Outlet />;
}