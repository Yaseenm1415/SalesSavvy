import { Link } from "react-router-dom";
import "../css/productCard.css";
export default function ProductCard({ product }) {
    console.log(`https://salessavvy-backend-8f97.onrender.com${product.images?.[0]?.imageUrl}`);
    return (
        <div className="product-card">
            <img src={product.images?.length ? `https://salessavvy-backend-8f97.onrender.com${product.images[0].imageUrl}` : "/no-image.png"}
            alt={product.name}/>
            

            <h3>{product.name}</h3>
            <p>{product.category.categoryName}</p>
            <h2>₹{product.price}</h2>

            <Link to={`/products/${product.productId}`}>
                <button className="btn-click-effect">View Details</button>
            </Link>

        </div>
    );
}