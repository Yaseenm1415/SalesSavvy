import api from "../../api/axios";

export const addToCart = async (productId, quantity) => {
    const response = await api.post("/api/cart", {productId, quantity });
    return response.data;
};

export const getCart = async () => {
    const response = await api.get("/api/cart");
    return response.data;
};

export const updateQuantity = async (cartId, quantity) => {
    const response = await api.put(`/api/cart/${cartId}`, {quantity});
    return response.data;
};

export const removeFromCart = async (cartId) => {
    const response = await api.delete(`/api/cart/${cartId}`);
    return response.data;
};