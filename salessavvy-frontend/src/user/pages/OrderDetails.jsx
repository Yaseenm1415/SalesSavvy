import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getOrderById } from "../services/orderService";
import { cancelOrder } from "../services/orderService";
import "../css/orders.css";
import { createRazorpayOrder, verifyPayment } from "../services/paymentService";
import { toast } from "react-toastify";

export default function OrderDetails() {

    const { orderId } = useParams();
    const [order, setOrder] = useState();

    const loadOrder = async () => {
        try {
            const response = await getOrderById(orderId);
            setOrder(response);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadOrder();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [orderId]);

    if (!order) {
        return <div className="loading-indicator">Loading order details...</div>
    }

    const handlePayment = async () => {
        const payment = await createRazorpayOrder(order.orderId);

        const options = {
            key: payment.key,
            amount: payment.amount,
            currency: payment.currency,
            order_id: payment.razorpayOrderId,

            handler: async function (response) {
                console.log(response);
                try {
                    await verifyPayment({
                        orderId: order.orderId,
                        razorpayOrderId: response.razorpay_order_id,
                        razorpayPaymentId: response.razorpay_payment_id,
                        razorpaySignature: response.razorpay_signature
                    });
                    toast.success("Payment successful!");
                    loadOrder();
                } catch (error) {
                    console.error(error);
                    toast.error("Payment failed!");
                }

            }
        };
        const razorpay = new window.Razorpay(options);
        razorpay.open();

    };

    const handleCancelOrder = async () => {

        const confirmCancel = window.confirm(
            "Are you sure you want to cancel this order?"
        );

        if (!confirmCancel) return;

        try {

            await cancelOrder(order.orderId);

            toast.success("Order cancelled successfully!");

            loadOrder();

            
        } catch (error) {

            console.error(error);
            toast.error(error.response?.data || "Failed to cancel order.");

        }
    };

    return (
        <div className="order-details-container">
            <h1 className="page-title">Order Details</h1>

            <div className="order-header-card">
                <div className="header-info">
                    <h2>Order #{order.orderId}</h2>
                    <p className="order-date">
                        Placed on: {new Date(order.createdAt).toLocaleString()}
                    </p>
                </div>
                <div className="header-status">
                    <span className={`status-badge ${order.status.toLowerCase()}`}>
                        {order.status}
                    </span>
                </div>
            </div>

            <div className="order-details-layout">
                <div className="order-items-list">
                    <h3>Items in Order</h3>
                    {order.items.map(item => (
                        
                        <div key={item.productId} className="order-item-row">
                            <div className="item-img-container">
                                <img src={item.imageUrl}
                                    alt={item.productName} />
                            </div>
                            <div className="item-details-container">
                                <h4>{item.productName}</h4>
                                <p className="item-meta">
                                    Quantity: {item.quantity} × ₹{item.priceAtPurchase}
                                </p>
                            </div>
                            <div className="item-subtotal-price">
                                ₹{item.subtotal}
                            </div>
                        </div>
                    ))}
                </div>

                <div className="order-total-summary">
                    <div className="summary-card">
                        <h3>Payment Details</h3>
                        <div className="summary-row">
                            <span>Subtotal</span>
                            <span>₹{order.totalAmount}</span>
                        </div>
                        <div className="summary-row">
                            <span>Delivery</span>
                            <span className="free-shipping-text">FREE</span>
                        </div>
                        <hr className="summary-divider" />
                        <div className="summary-row total-row">
                            <span>Total Amount</span>
                            <strong>₹{order.totalAmount}</strong>
                        </div>
                        {order.status === "PENDING" && (
                            <button className="pay-button btn-click-effect" onClick={handlePayment}>
                                Pay Now
                            </button>
                        )}
                        <div className="order-actions">

                            {(order.status === "PENDING" ||
                                order.status === "CONFIRMED") && (

                                    <button
                                        className="cancel-btn btn-click-effect"
                                        onClick={handleCancelOrder}
                                    >
                                        Cancel Order
                                    </button>

                                )}

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
