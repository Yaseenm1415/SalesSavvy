import api from "../../api/axios";

export const createRazorpayOrder = async (orderId) => {
    const response = await api.post(`/api/payment/razorpay-order/${orderId}`);
    return response.data;
}

export const verifyPayment = async (data) => {
const response = await api.post("/api/payment/verify", data);
return response.data;
}