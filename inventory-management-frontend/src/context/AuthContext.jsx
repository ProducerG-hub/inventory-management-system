import { createContext, useContext, useState } from "react";
import authStorage from "../utils/authStorage";


const AuthContext = createContext();


export const AuthProvider = ({ children }) => {


    const [user, setUser] = useState(
        authStorage.getUser()
    );


    const [token, setToken] = useState(
        authStorage.getToken()
    );


    const login = (userData, accessToken) => {

        authStorage.setUser(userData);

        authStorage.setToken(accessToken);


        setUser(userData);

        setToken(accessToken);

    };


    const logout = () => {

        authStorage.clear();


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