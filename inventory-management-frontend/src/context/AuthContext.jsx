import { createContext, useCallback, useContext, useState } from "react";
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


    const updateUser = useCallback((userData) => {

        setUser((currentUser) => {

            const updatedUser = {
                ...currentUser,
                ...userData
            };

            storage.setUser(updatedUser);

            return updatedUser;

        });

    }, []);


    const value = {

        user,

        token,

        login,

        updateUser,

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
