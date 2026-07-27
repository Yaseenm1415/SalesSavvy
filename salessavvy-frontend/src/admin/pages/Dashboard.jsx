import { useEffect, useState } from "react"
import { getDashboard } from "../services/adminDashboardService";
import "../css/adminDashboard.css";
export default function Dashboard() {
    const [dashboard, setDashboard] = useState(null);
    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            const response = await getDashboard();
            setDashboard(response);
        } catch (error) {
            console.error(error);
        }
    };
    if(!dashboard) {
        return <h2>Loading...</h2>;
    }
    return (
        <div className="dashboard-container">
            <h1>Dashboard</h1>
            <div className="dashboard-cards">

                <div className="dashboard-card">
                    <h3>Total Users</h3>
                    <p>{dashboard.totalUsers}</p>
                </div>

                <div className="dashboard-card">
                    <h3>Total Products</h3>
                    <p>{dashboard.totalProducts}</p>
                </div>

                <div className="dashboard-card">
                    <h3>Total Orders</h3>
                    <p>{dashboard.totalOrders}</p>
                </div>

                <div className="dashboard-card revenue">
                    <h3>Total Revenue</h3>
                    <p>{dashboard.totalRevenue}</p>
                </div>

                <div className="dashboard-card pending">
                    <h3>Pending Orders</h3>
                    <p>{dashboard.pendingOrders}</p>
                </div>
            </div>
        </div>
    );
}