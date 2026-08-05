import api from "../../api/axios";

export const getAllOrders = async () => {
    const response = await api.get("/admin/orders");
    return response.data;
};

export const getOrderById = async (id) => {
    const response = await api.get(`/admin/orders/${id}`);
    return response.data;
};

export const updateOrderStatus = async (orderId, status) => {
    const response = await api.put(
        `/admin/orders/${orderId}/status`,
        { status }
    );

    return response.data;
};