import { useEffect, useState } from "react"
import { deleteProduct, getAllProducts } from "../services/adminProducts";
import { useNavigate } from "react-router-dom";
import "../css/product.css";
import { toast } from "react-toastify";

export default function Products() {

    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    const loadProducts = async () => {
        try {
            const response = await getAllProducts();
            setProducts(response.content);
            console.log(response.content);
        } catch (error) {
            console.error(error);
        };
    }

    useEffect(() => {
        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadProducts();
    }, []);

    const handleDeleteProduct = async (productId) => {
        try {
            await deleteProduct(productId);
            loadProducts();
            toast.success("Product deleted successfully!");
        } catch (error) {
            console.error(error);
        }
    } 

    if (!products) {
        return <div className="loading-indicator">Loading products...</div>
    }


    return (
        <div className="admin-products">
            <div className="page-header">
                <h2>Products</h2>
                <button className="add-btn btn-click-effect" onClick={() => navigate("/admin/products/new")}>+ Add Products</button>
            </div>

            <div className="table-responsive">
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
                                        src={product.images?.length > 0
                                            ? product.images[0].imageUrl
                                            : "/no-image.png"}
                                        alt={product.name}
                                        className="product-image"
                                    />
                                </td>

                                <td>{product.name}</td>
                                <td>{product.category.categoryName}</td>
                                <td>₹{product.price}</td>
                                <td>{product.stock}</td>
                                <td>
                                    <button className="edit-btn btn-click-effect" onClick={() => navigate(`/admin/products/edit/${product.productId}`)}>Edit</button>
                                    <button className="delete-btn btn-click-effect" onClick={() => handleDeleteProduct(product.productId)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}