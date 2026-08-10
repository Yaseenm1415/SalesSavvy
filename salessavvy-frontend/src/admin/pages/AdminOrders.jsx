import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAllOrders } from "../services/adminOrderService";
import "../css/adminOrders.css";

export default function AdminOrders() {

    const [orders, setOrders] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const loadOrders = async () => {
            try {
                const response = await getAllOrders();
                setOrders(response);
            } catch (error) {
                console.error(error);
            }
        };

        loadOrders();
    }, []);

    if (!orders) {
        return <div className="loading-indicator">Loading orders...</div>;
    }

    const formatDate = (date) => {
        return new Date(date).toLocaleDateString("en-IN", {
            day: "2-digit",
            month: "short",
            year: "numeric",
        });
    };

    const formatStatus = (status) => {
        return status.charAt(0) + status.slice(1).toLowerCase();
    };

    return (
        <div className="admin-orders">

            <div className="page-header">
                <h2>Orders</h2>
            </div>

            <table className="orders-table">

                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Email</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>

                    {orders.length === 0 ? (
                        <tr>
                            <td colSpan="7">
                                No Orders Found
                            </td>
                        </tr>
                    ) : (
                        orders.map(order => (

                            <tr key={order.orderId}>

                                <td>#{order.orderId}</td>

                                <td>{order.customerName}</td>

                                <td>{order.customerEmail}</td>

                                <td>{formatDate(order.createdAt)}</td>

                                <td>₹{order.totalAmount}</td>

                                <td>
                                    <span
                                        className={`status ${order.status.toLowerCase()}`}
                                    >
                                        {formatStatus(order.status)}
                                    </span>
                                </td>

                                <td>
                                    <button
                                        className="view-btn btn-click-effect"
                                        onClick={() =>
                                            navigate(`/admin/orders/${order.orderId}`)
                                        }
                                    >
                                        View
                                    </button>
                                </td>

                            </tr>

                        ))
                    )}

                </tbody>

            </table>

        </div>
    );
}