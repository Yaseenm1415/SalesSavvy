import { useEffect, useState } from "react";
import { getCategories } from "../services/categoryService";
import { getProducts } from "../services/productService";
import ProductCard from "../components/ProductCard";
import { useAuth } from "../../context/AuthContext";
import { Navigate } from "react-router-dom";
import "../css/home.css";
export default function Home() {
    const [keyword, setKeyword] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const { user } = useAuth();

    useEffect(() => {
        async function loadProducts() {
            try {
                const response = await getProducts(
                    keyword,
                    categoryId,
                    page,
                    8
                );
                setProducts(response.content);
                setTotalPages(response.totalPages);
            } catch (error) {
                console.error(error);
            }
        }

        async function loadCategories() {
            try {
                const response = await getCategories();
                setCategories(response);
            } catch (error) {
                console.error(error);
            }
        }

        loadProducts();
        loadCategories();
    }, [page, keyword, categoryId]);

    if (user?.role === "ADMIN") {
        return <Navigate to="/admin/dashboard" replace />;
    }
    return (
        <div className="home-container">
            <h1 className="page-title">SalesSavvy Products</h1>

            <div className="filter-bar">

                <div className="search-bar">
                    <input
                        type="text"
                        placeholder="Search products..."
                        value={keyword}
                        onChange={(e) => {
                            setKeyword(e.target.value);
                            setPage(0);
                        }}
                    />
                </div>

                <div className="category-filter">
                    <select
                        value={categoryId}
                        onChange={(e) => {
                            setCategoryId(e.target.value);
                            setPage(0);
                        }}
                    >
                        <option value="">All Categories</option>

                        {categories.map(category => (
                            <option
                                key={category.categoryId}
                                value={category.categoryId}
                            >
                                {category.categoryName}
                            </option>
                        ))}
                    </select>
                </div>

            </div>

            <div className="products-grid">
                {products.map((product) => (
                    <ProductCard
                        key={product.productId}
                        product={product}
                    />
                ))}
            </div>
            <div className="pagination">

                <button
                    disabled={page === 0}
                    onClick={() => setPage(page - 1)}
                >
                    Previous
                </button>

                {Array.from({ length: totalPages }, (_, index) => (

                    <button
                        key={index}
                        className={page === index ? "active-page" : ""}
                        onClick={() => setPage(index)}
                    >
                        {index + 1}
                    </button>

                ))}

                <button
                    disabled={page === totalPages - 1}
                    onClick={() => setPage(page + 1)}
                >
                    Next
                </button>

            </div>
        </div>
    );
}