import api from "../../api/axios";

export const login = async (loginRequest) => {
    const response = await api.post("/api/user/login", loginRequest);
    return response.data;
};

export const register = async (registerRequest) => {
    const response = await api.post("/api/user/register", registerRequest);
    return response.data;
}

export const getCurrentUser = async () => {
    const response = await api.get("/me");
    return response.data;
};

export const logout = async () => {
    const response = await api.post("/logout");
    return response.data;
};
