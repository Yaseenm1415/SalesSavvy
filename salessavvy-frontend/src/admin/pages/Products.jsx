import { useEffect, useState } from "react"
import { getAllProducts } from "../services/adminProducts";

export default function Products() {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        loadProducts();
    }, []);

    const loadProducts = async () => {
        try {
            const response = await getAllProducts();
            setProducts(response.content);
            console.log(response.content);
        } catch (error) {
            console.error(error);
        };  
    }

    if(!products) {
        return <p>Loading...</p>
    }  

    return (
        <div className="admin-products">
            <div className="page-header">
                <h2>Products</h2>
                <button className="add-btn">+ Add Products</button>
            </div>

            <table className="product-table">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>name</th>
                        <th>category</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {products.map(product => (
                        <tr key={product.productId}>
                            <td>
                                <img  
                                    src={`http://localhost:9090${product.imageUrls[0]}`}
                                    alt={product.name}
                                    className="product-image"
                                />    
                            </td>

                            <td>{product.name}</td>
                            <td>{product.categoryName}</td>
                            <td>₹{product.price}</td>
                            <td>{product.stock}</td>
                            <td>
                                <button className="edit-btn">Edit</button>
                                <button className="delete-btn">Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}