import { useEffect, useState } from "react"
import { getCart, updateQuantity, removeFromCart } from "../services/cartService"
import { placeOrder } from "../services/orderService";
import { useNavigate } from "react-router-dom";
import "../css/cart.css";
import { toast } from "react-toastify";

export default function Cart() {
    const [cartItems, setcartItems] = useState([]);
    const navigate = useNavigate();

    const loadCart = async () => {
        try {
            const data = await getCart();
            setcartItems(data);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadCart();
    }, []);

    const increaseQuantity = async (item) => {
        await updateQuantity(item.cartId, item.quantity + 1);
        loadCart();
    };

    const decreaseQuantity = async (item) => {
        if (item.quantity === 1) {
            await removeFromCart(item.cartId);
        } else {
            await updateQuantity(item.cartId, item.quantity - 1);

        }
        loadCart();
    };

    const handleRemove = async (cartId) => {
        await removeFromCart(cartId);

        loadCart();
    };

    const total = cartItems.reduce(
        (sum, item) => sum + item.subtotal, 0
    );

    const handleCheckout = async () => {
        try {
            const cartItemsIds = cartItems.map(item => item.cartId);
            await placeOrder(cartItemsIds);
            toast.success("Order placed successfully!");
            navigate("/orders");
        } catch (error) {
            console.error(error);
            toast.error("Failed to place order.");
        }
    }

    if (cartItems.length === 0) {
        return (
            <div className="empty-cart-container">
                <div className="empty-cart-icon">🛒</div>
                <h2>Your cart is empty</h2>
                <p>Looks like you haven't added anything to your cart yet.</p>
                <button className="btn-primary-standard btn-click-effect" onClick={() => navigate("/")}>
                    Start Shopping
                </button>
            </div>
        );
    }
    return (
        <div className="cart-page-container">
            <h1 className="page-title">Shopping Cart</h1>

            <div className="cart-layout">
                <div className="cart-items-list">
                    {cartItems.map(item => (
                        <div className="cart-item" key={item.cartId}>
                            <div className="item-image-wrapper">
                                <img
                                    src={item.imageUrl}
                                    alt={item.productName}
                                    onClick={() => navigate(`/products/${item.productId}`)}
                                />
                            </div>

                            <div className="item-details">
                                <h3 className="item-title" onClick={() => navigate(`/products/${item.productId}`)}>
                                    {item.productName}
                                </h3>
                                <div className="item-price">₹{item.price}</div>

                                <div className="item-controls-row">
                                    <div className="quantity-selector">
                                        <button className="qty-btn btn-click-effect" onClick={() => decreaseQuantity(item)}>-</button>
                                        <span className="qty-value">{item.quantity}</span>
                                        <button className="qty-btn btn-click-effect" onClick={() => increaseQuantity(item)}>+</button>
                                    </div>

                                    <button className="remove-item-btn btn-click-effect" onClick={() => handleRemove(item.cartId)}>
                                        Remove
                                    </button>
                                </div>
                            </div>

                            <div className="item-subtotal">
                                <span>Subtotal</span>
                                <strong>₹{item.subtotal}</strong>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="cart-summary-sidebar">
                    <div className="summary-card">
                        <h2 className="summary-title">Order Summary</h2>
                        <div className="summary-details">
                            <div className="summary-row">
                                <span>Items ({cartItems.reduce((acc, item) => acc + item.quantity, 0)})</span>
                                <span>₹{total.toFixed(2)}</span>
                            </div>
                            <div className="summary-row">
                                <span>Delivery</span>
                                <span className="delivery-free">FREE</span>
                            </div>
                            <hr className="summary-divider" />
                            <div className="summary-row total-row">
                                <span>Total:</span>
                                <strong>₹{total.toFixed(2)}</strong>
                            </div>
                        </div>

                        <button className="proceed-checkout-btn btn-click-effect" onClick={handleCheckout}>
                            Proceed to Checkout
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
