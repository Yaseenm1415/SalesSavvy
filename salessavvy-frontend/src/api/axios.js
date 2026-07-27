import axios from "axios";
import { LOGOUT_EVENT } from "../utils/authEvents";

const api = axios.create({
    baseURL: "http://localhost:9090",
    withCredentials: true,
});

api.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        const authUrls = [
            "/api/user/login",
            "/api/user/register",
            "/refresh",
        ];

        if (error.response?.status === 401 && !originalRequest._retry && !authUrls.includes(originalRequest.url)) {

            originalRequest._retry = true;

            try {
                await api.post("/refresh");
                return api(originalRequest);
            } catch (refreshError) {
                window.dispatchEvent(new Event(LOGOUT_EVENT));
                return Promise.reject(refreshError);
            }

        }
        return Promise.reject(error);
    }
);
export default api;