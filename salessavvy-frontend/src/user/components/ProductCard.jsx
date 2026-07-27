import { Link } from "react-router-dom";
import "../css/productCard.css";
export default function ProductCard({ product }) {
    return (
        <div className="product-card">
            <img src={product.imageUrls?.length ? `http://localhost:9090${product.imageUrls[0]}` : "https://via.placeholder.com/250x200?text=No+Image"}
            alt={product.name}/>

            <h3>{product.name}</h3>
            <p>{product.categoryName}</p>
            <h2>₹{product.price}</h2>

            <Link to={`/products/${product.productId}`}>
                <button>Veiw Details</button>
            </Link>

        </div>
    );
}