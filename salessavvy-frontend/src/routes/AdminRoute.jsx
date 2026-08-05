import { Outlet, Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext"

export default function AdminRoute() {
    const { user , loading} = useAuth();

    if(loading) {
        return <h2>Loading...</h2>
    }
    if (!user || user.role !== "ADMIN") {
        return <Navigate to = "/admin/login" replace />;
    }

    return <Outlet />;
}