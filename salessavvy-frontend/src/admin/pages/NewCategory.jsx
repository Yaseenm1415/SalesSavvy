import { useState } from "react";
import { createCategory } from "../services/adminCategoryService";
import { useNavigate } from "react-router-dom";
import "../css/edit-newCategory.css";
import { toast } from "react-toastify";

export default function NewCategory() {
    const [categoryName, setCategoryName] = useState("");
    const navigate = useNavigate();
    const handleCreateCategory = async (e) => {
            e.preventDefault();
        try {
            await createCategory({categoryName});
            toast.success(`New Category Saved '${categoryName}'`);
            navigate("/admin/categories");
        } catch (error) {
            console.error(error);
        }

    }
    return (
        <div className="category-form-container">
            <h2>Add Category</h2>

            <form className="category-form" onSubmit={handleCreateCategory}>
                <label>Category Name</label>
                <input
                    type="text"
                    name="categoryName"
                    value={categoryName}
                    onChange={(e) => setCategoryName(e.target.value)}
                    required
                />
                <button type="submit" className="submit-btn btn-click-effect">Save Category</button>
            </form>
        </div>
    );
}