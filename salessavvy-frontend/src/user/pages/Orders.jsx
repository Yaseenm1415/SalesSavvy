import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";
import { getOrders } from "../services/orderService";
import "../css/orders.css";

export default function Orders() {
    const [orders, setOrders] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const loadOrders = async () => {
            try {
                const response = await getOrders();
                setOrders(response);
            } catch (error) {
                console.error(error);
            }
        };

        loadOrders();
    }, []);

    if (orders.length === 0) {
        return (
            <div className="empty-orders-container">
                <div className="empty-orders-icon">📦</div>
                <h2>No Orders Found</h2>
                <p>Looks like you haven't placed any orders yet.</p>
                <button className="btn-primary-standard btn-click-effect" onClick={() => navigate("/")}>
                    Start Shopping
                </button>
            </div>
        );
    }
    return (
        <div className="orders-page-container">
            <h1 className="page-title">My Orders</h1>
            <div className="orders-list">
                {orders.map(order => (
                    <div key={order.orderId} className="order-card">
                        <div className="order-info-section">
                            <h2 className="order-card-title">Order #{order.orderId}</h2>
                            
                            <div className="order-meta-grid">
                                <div className="meta-cell">
                                    <span className="label">Date</span>
                                    <span className="value">{new Date(order.createdAt).toLocaleDateString()}</span>
                                </div>
                                <div className="meta-cell">
                                    <span className="label">Total</span>
                                    <span className="value price-value">₹{order.totalAmount}</span>
                                </div>
                                <div className="meta-cell">
                                    <span className="label">Items</span>
                                    <span className="value">{order.items.length} items</span>
                                </div>
                                <div className="meta-cell">
                                    <span className="label">Status</span>
                                    <span className={`status-badge ${order.status.toLowerCase()}`}>
                                        {order.status}
                                    </span>
                                </div>
                            </div>
                        </div>

                        <button
                            className="btn-secondary-standard view-details-btn btn-click-effect"
                            onClick={() => navigate(`/orders/${order.orderId}`)}>
                            View Details
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}