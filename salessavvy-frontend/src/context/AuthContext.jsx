import { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser } from "../user/services/authService";
import { LOGOUT_EVENT } from "../utils/authEvents";
const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [user, setUser] = useState(null);

    useEffect(() => {
        const loadUser = async () => {
            try {
                const response = await getCurrentUser();
                setUser(response);
            } catch (error) {
                console.error(error);
                setUser(null);
            }
        };

        loadUser();
    }, []);

    const logout = () => {
        setUser(null);
    }

    useEffect(() => {
        window.addEventListener(LOGOUT_EVENT, logout);

        return () => {
            window.removeEventListener(LOGOUT_EVENT, logout);
        };
    }, []);

    return (
        <AuthContext.Provider value={{user, setUser, logout}}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}

export default AuthContext;
