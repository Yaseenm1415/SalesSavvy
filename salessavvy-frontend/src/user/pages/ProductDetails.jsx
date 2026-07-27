import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom"
import { getProductById } from "../services/productService";
import { addToCart } from "../services/cartService";
import "../css/productDetails.css";
import { useAuth } from "../../context/AuthContext";

export default function ProductDetails() {

    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [selectedImage, setSelectedImage] = useState(0);
    const { user } = useAuth();
    const navigate = useNavigate();
    const handleAddTocart = async () => {
        if (!user) {
            navigate("/login");
            return;
        }
        try {
            await addToCart(product.productId, 1);
            alert("Product added to cart!");
        } catch (error) {
            console.error(error);
            alert("Failed to add product.");
        }
    };

    useEffect(() => {
        const loadProduct = async () => {
            try {
                const data = await getProductById(id);
                setProduct(data);
            } catch (error) {
                console.error(error);
            }
        };
        loadProduct();
    }, [id]);

    if (!product) {
        return <h2>Loading...</h2>
    }

    return (
        <div className="product-details-container">
            <div className="product-gallery-section">
                <div className="main-image-wrapper">
                    <img src={`http://localhost:9090${product.imageUrls?.[selectedImage]}`}
                        alt={product.name} />
                </div>

                <div className="thumbnails-wrapper">
                    {product.imageUrls?.map((image, index) => (
                        <img src={`http://localhost:9090${image}`}
                            key={index}
                            className={selectedImage === index ? "active" : ""}
                            alt=""
                            onClick={() => setSelectedImage(index)} />
                    ))}
                </div>
            </div>

            <div className="product-info-section">
                <h1 className="product-title">{product.name}</h1>
                
                <div className="product-meta-info">
                    <span className="meta-item">
                        <strong>Category:</strong> {product.categoryName}
                    </span>
                    <span className="meta-item">
                        <strong>Stock:</strong> {product.stock} units
                    </span>
                </div>

                <div className="product-description-container">
                    <h3>Description</h3>
                    <p className="product-description">{product.description}</p>
                </div>
            </div>

            <div className="product-purchase-card">
                <div className="price-tag">
                    <span className="currency-symbol">₹</span>
                    <span className="price-value">{product.price}</span>
                </div>
                
                <div className="stock-badge in-stock">
                    {product.stock > 0 ? "✓ In Stock" : "✗ Out of Stock"}
                </div>
                
                <button 
                    className="add-to-cart-btn" 
                    disabled={product.stock <= 0}
                    onClick={handleAddTocart}
                >
                    Add To Cart
                </button>
            </div>
        </div>
    )
}