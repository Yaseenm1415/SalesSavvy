import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
    getOrderById,
    updateOrderStatus
} from "../services/adminOrderService";
import "../css/adminOrderDetails.css";
import { toast } from "react-toastify";

export default function AdminOrderDetails() {

    const { id } = useParams();

    const [order, setOrder] = useState(null);
    const [status, setStatus] = useState("");

    const loadOrder = async () => {
        try {
            const response = await getOrderById(id);

            setOrder(response);
            setStatus(response.status);

        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadOrder();
    
    }, []);

    const handleUpdateStatus = async () => {

        try {

            await updateOrderStatus(order.orderId, status);

            toast.success("Order status updated successfully!");

            loadOrder();

        } catch (error) {
            console.error(error);
            toast.error("Failed to update status.");
        }

    };

    if (!order) {
        return <div className="loading-indicator">Loading order details...</div>;
    }

    return (

        <div className="order-details">

            <h2>Order #{order.orderId}</h2>

            <div className="customer-card">

                <div className="customer-header">

                    <h3>Customer Details</h3>

                    <div className="status-section">

                        <span
                            className={`status ${status.toLowerCase()}`}
                        >
                            {status}
                        </span>

                        <select
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                        >

                            <option value="PENDING">
                                Pending
                            </option>

                            <option value="CONFIRMED">
                                Confirmed
                            </option>

                            <option value="SHIPPED">
                                Shipped
                            </option>

                            <option value="DELIVERED">
                                Delivered
                            </option>

                            <option value="CANCELLED">
                                Cancelled
                            </option>

                        </select>

                        <button
                            className="update-btn btn-click-effect"
                            onClick={handleUpdateStatus}
                        >
                            Update
                        </button>

                    </div>

                </div>

                <div className="customer-grid">

                    <div>
                        <span>Name</span>
                        <h4>{order.customerName}</h4>
                    </div>

                    <div>
                        <span>Email</span>
                        <h4>{order.customerEmail}</h4>
                    </div>

                    <div>
                        <span>Order Date</span>
                        <h4>
                            {new Date(order.createdAt).toLocaleDateString(
                                "en-IN",
                                {
                                    day: "2-digit",
                                    month: "short",
                                    year: "numeric",
                                }
                            )}
                        </h4>
                    </div>

                    <div>
                        <span>Total Amount</span>
                        <h4>₹{order.totalAmount}</h4>
                    </div>

                </div>

            </div>

            <h3>Ordered Products</h3>

            <div className="products-list">

                {order.items.map(item => (

                    <div
                        className="product-card"
                        key={item.productId}
                    >

                        <img
                            src={`${item.imageUrl}`}
                            alt={item.productName}
                        />

                        <div className="product-info">

                            <h4>{item.productName}</h4>

                            <p>
                                <strong>Quantity :</strong> {item.quantity}
                            </p>

                            <p>
                                <strong>Price :</strong> ₹{item.priceAtPurchase}
                            </p>

                            <p>
                                <strong>Subtotal :</strong> ₹{item.subtotal}
                            </p>

                        </div>

                    </div>

                ))}

            </div>

            <div className="order-summary">

                <h3>Order Summary</h3>

                <div className="summary-row">

                    <span>Total Items</span>

                    <strong>
                        {
                            order.items.reduce(
                                (sum, item) => sum + item.quantity,
                                0
                            )
                        }
                    </strong>

                </div>

                <div className="summary-row">

                    <span>Total Amount</span>

                    <strong>
                        ₹{order.totalAmount}
                    </strong>

                </div>

            </div>

        </div>

    );

}