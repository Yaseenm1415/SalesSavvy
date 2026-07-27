import api from "../../api/axios";

export const placeOrder = async (cartItemsIds) => {
    const response = await api.post("/api/orders", { cartItemsIds });
    return response.data;
};

export const getOrders = async () => {

    const response = await api.get("/api/orders");

    return response.data;
};

export const getOrderById = async (orderId) => {

    const response = await api.get(`/api/orders/${orderId}`);

    return response.data;
};