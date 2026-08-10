import { useEffect, useState } from "react";
import { deleteCategory, getCategories } from "../services/adminCategoryService";
import { useNavigate } from "react-router-dom";
import "../css/adminCategory.css";
import { toast } from "react-toastify";

export default function Categories() {
    const [categories, setCategories] = useState(null);
    const navigate = useNavigate();

    const loadCategories = async() => {
        try {
            const response = await getCategories();
            setCategories(response);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        loadCategories();
    },[]);

    if (!categories) {
        return <div className="loading-indicator">Loading categories...</div>;
    }

    const handleDeleteCategory = async(id) => {
        try {
            await deleteCategory(id);
            toast.success("Category deleted successfully");
            loadCategories();
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <div className="admin-categories">
            <div className="page-header">
                <h2>Categories</h2>
                <button className="add-btn btn-click-effect" onClick={() => navigate("/admin/categories/new")}>+ Add Category</button>
            </div>

            <table className="categories-table">
                <thead>
                    <tr>
                        <th>Category Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {categories.map(category => (
                        <tr key={category.categoryId}>
                            <td>{category.categoryName}</td>
                            <td>
                                <button className="edit-btn btn-click-effect" onClick={() => navigate(`/admin/categories/edit/${category.categoryId}`)}>Edit</button>
                                <button className="delete-btn btn-click-effect" onClick={() => handleDeleteCategory(category.categoryId)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}