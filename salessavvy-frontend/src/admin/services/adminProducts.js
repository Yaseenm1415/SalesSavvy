import api from "../../api/axios";

export const getAllProducts = async () => {
    const response = await api.get("/api/products");
    return response.data;
}