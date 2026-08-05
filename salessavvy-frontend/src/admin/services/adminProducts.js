import api from "../../api/axios";

export const getAllProducts = async () => {
    const response = await api.get("/api/products");
    return response.data;
}

export const getProductById = async (id) => {
    const response = await api.get(`/api/products/${id}`);
    return response.data;
}

export const createProduct = async (product) => {
    const response = await api.post("/admin/products", product)
    return response.data;
}
export const updateProduct = async (id, product) => {
    const response = await api.put(`/admin/products/${id}`, product);
    return response.data;
}

export const deleteProduct = async (id) => {
    const response = await api.delete(`admin/products/${id}`);
    return response.data;
}

export const uploadProductImage = async (productId, images) => {
    const formData = new FormData();

    images.forEach(image => {
        formData.append("images", image);
    });

    const response = await api.post(`/admin/products/${productId}/images`, formData);
    return response.data;
}

export const deleteProductImage = async (imageId) => {
    await api.delete(`/admin/products/images/${imageId}`);
};
