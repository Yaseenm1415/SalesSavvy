import { useEffect, useState } from "react";

import { getAllProducts } from "../services/productService";
import ProductCard from "../components/ProductCard";

export default function Home() {
    const [products, setProducts] = useState([]);
   
    useEffect(() => {
        const loadProducts = async () => {
            try {
                const data = await getAllProducts();
                setProducts(data);
            } catch (error) {
                console.error(error);
            }
        };

        loadProducts();
    }, []);
        console.log(products)
    return (
        <div className="home-container">
            <h1 className="page-title">SalesSavvy Products</h1>

            <div className="products-grid">
                {products.map((product) => (
                    <ProductCard 
                    key={product.productId}
                    product={product}
                    />
                ))}
            </div>
        </div>
    );
}