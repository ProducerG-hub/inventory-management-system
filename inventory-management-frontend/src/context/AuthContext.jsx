import { createContext, useContext } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const user = {
        username: "Admin",
        role: "ADMIN"
    };

    return (
        <AuthContext.Provider value={{ user }}>
            {children}
        </AuthContext.Provider>
    );

};


export const useAuth = () => {

    return useContext(AuthContext);

};