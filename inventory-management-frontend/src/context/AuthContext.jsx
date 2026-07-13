import { createContext, useContext, useState } from "react";
import storage from "../utils/authStorage";


const AuthContext = createContext();


export const AuthProvider = ({ children }) => {


    const [user, setUser] = useState(
        storage.getUser()
    );


    const [token, setToken] = useState(
        storage.getToken()
    );


    const login = (userData, accessToken) => {

        storage.setUser(userData);

        storage.setToken(accessToken);


        setUser(userData);

        setToken(accessToken);

    };


    const logout = () => {

        storage.clear();


        setUser(null);

        setToken(null);

    };


    const value = {

        user,

        token,

        login,

        logout,

        isAuthenticated: !!token

    };


    return (

        <AuthContext.Provider value={value}>

            {children}

        </AuthContext.Provider>

    );

};



export const useAuth = () => {

    return useContext(AuthContext);

};