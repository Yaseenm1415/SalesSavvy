import { useEffect, useState } from "react";
import { getCategories } from "../services/adminCategoryService";
import "../css/productForm.css";
import { createProduct, deleteProductImage, getProductById, updateProduct, uploadProductImage } from "../services/adminProducts";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";

export default function ProductForm({ mode }) {
    const [product, setproduct] = useState({
        name: "",
        description: "",
        price: "",
        stock: "",
        categoryId: ""
    });

    const [images, setImages] = useState([]);
    const [existingImages, setExistingImages] = useState([]);
    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();

    const loadCategories = async () => {
        try {
            const response = await getCategories();
            setCategories(response);
        } catch (error) {
            console.error(error);
        }
    };

    const loadProduct = async () => {
        try {
            const response = await getProductById(id);
            console.log(response);
            setproduct({
                name: response.name,
                description: response.description,
                price: response.price,
                stock: response.stock,
                categoryId: response.category.categoryId
            });

            setExistingImages(response.images);

        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {

        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadCategories();

        if (mode === "edit") {
            loadProduct();
        }

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleChange = (e) => {
        setproduct({
            ...product, [e.target.name]: e.target.value
        });
    };

    const handleAddProduct = async (e) => {
        e.preventDefault();
        try {

            if (!product.categoryId) {
                toast.warning("Please select a category");
                return;
            }

            const createdProduct = await createProduct(product);

            if (images.length > 0) {
                await uploadProductImage(createdProduct.productId, images);
            }

            toast.success("Product added successfully!");
            navigate("/admin/products");
        } catch (error) {
            console.error(error);
        }
    };

    const handleUpdateProduct = async (e) => {
        e.preventDefault();

        try {
            await updateProduct(id, product);

            if (images.length > 0) {
                await uploadProductImage(id, images);
            }

            toast.success("Product updated successfully!");
            navigate("/admin/products");
        } catch (error) {
            console.error(error);
        }
    };

    const handleDeleteImage = async (imageId) => {
        try {

            await deleteProductImage(imageId);

            setExistingImages(prev =>
                prev.filter(img => img.imageId !== imageId)
            );

        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="product-form-container">
            <h2>
                {mode === "create" ? "Add Product" : 'Edit Product'}
            </h2>

            <form className="product-form" onSubmit={mode === "create" ? handleAddProduct : handleUpdateProduct}>
                <div className="form-group">
                    <label>Product Name</label>
                    <input
                        type="text"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-group">
                    <label>Description</label>
                    <textarea
                        name="description"
                        rows="5"
                        value={product.description}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-row">

                    <div className="form-group">
                        <label>Price</label>
                        <input
                            type="number"
                            name="price"
                            value={product.price}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Stock</label>
                        <input
                            type="number"
                            name="stock"
                            value={product.stock}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Category</label>
                        <select
                            name="categoryId"
                            value={product.categoryId}
                            onChange={handleChange}
                        >
                            <option value="">Select category</option>
                            {categories.map(category => (
                                <option key={category.categoryId} value={category.categoryId}>{category.categoryName}</option>

                            ))}
                        </select>
                    </div>

                    <div>
                        <label>Image</label>
                        <input
                            type="file"
                            multiple
                            onChange={(e) => {
                                const files = Array.from(e.target.files);
                            
                                setImages(prev => [...prev, ...files]);
                            
                                e.target.value = "";
                            }}
                        />
                    </div>
                </div>
                <div className="selected-images">
                    <h3>New Images</h3>

                    {images.length === 0 ? (
                        <p>No new images selected.</p>
                    ) : (
                        images.map((image, index) => (
                            <div className="selected-image-card" key={index}>
                                <img
                                    src={URL.createObjectURL(image)}
                                    alt=""
                                />

                                <button
                                    type="button"
                                    className="btn-click-effect"
                                    onClick={() =>
                                        setImages(prev => prev.filter((_, i) => i !== index))
                                    }
                                >
                                    Remove
                                </button>
                            </div>
                        ))
                    )}
                </div>
                {existingImages.length === 0 ? (
                    <p></p>
                ) : <div className="existing-images">
                    <h3>Images</h3>

                    {existingImages.map(image => (
                        <div key={image.imageId} className="existing-image-card">
                            <img
                                src={`http://localhost:9090${image.imageUrl}`}
                                alt=""
                            />

                            <button
                                type="button"
                                className="btn-click-effect"
                                onClick={() => handleDeleteImage(image.imageId)}
                            >
                                Delete
                            </button>
                        </div>
                    ))}
                </div>}


                <button type="submit" className="btn-click-effect">
                    {mode === "create" ? "Add Product" : "Update Product"}
                </button>

            </form>
        </div>
    );

}