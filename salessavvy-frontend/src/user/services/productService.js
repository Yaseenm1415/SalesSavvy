import api from "../../api/axios";

export const getAllProducts = async () => {
    const response = await api.get("/api/products");
    return response.data.content;
}

export const getProductById = async (id) => {
    const response = await api.get(`/api/products/${id}`);
    return response.data;
}

export const getProducts = async (
    keyword = "",
    categoryId = "",
    page = 0,
    size = 8
) => {

    const response = await api.get("/api/products", {
        params: {
            keyword,
            categoryId,
            page,
            size
        }
    });

    return response.data;
};

