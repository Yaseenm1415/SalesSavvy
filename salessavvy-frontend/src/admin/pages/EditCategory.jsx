import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import { getCategoryById, updateCategory } from "../services/adminCategoryService";
import "../css/edit-newcategory.css";
import { toast } from "react-toastify";

export default function EditCategory() {
    const [categoryName, setCategoryName] = useState("");
    const { id } = useParams();
    const navigate = useNavigate();

    const loadCategories = async () => {
        try {
            const response = await getCategoryById(id);
            setCategoryName(response.categoryName);
        } catch (error) {
            console.error(error);
        }
       
    }

    useEffect(() => {
        // eslint-disable-next-line react-hooks/set-state-in-effect
        loadCategories();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleUpdateCategory = async (e) => {
        e.preventDefault();

        try {
            await updateCategory(id, {categoryName});
            toast.success("Category Updated successfully!");
            navigate("/admin/categories");
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <div className="category-form-container">
            <h2>Edit Category</h2>

            <form className="category-form" onSubmit={handleUpdateCategory}>
                <label>Category Name</label>
                <input
                    type="text"
                    name="categoryName"
                    value={categoryName}
                    onChange={(e) => setCategoryName(e.target.value)}
                    required
                />   
                <button type="submit" className="submit-btn btn-click-effect">Update Category</button>
            </form>
        </div>
    )
}