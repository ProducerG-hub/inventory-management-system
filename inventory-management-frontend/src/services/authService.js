import axiosInstance from "../api/axiosConfig";
import API_ENDPOINTS from "../config/constants/apiEndpoints";
import storage from "../utils/authStorage";

const authService = {

    async login(credentials) {

        const response = await axiosInstance.post(

            API_ENDPOINTS.AUTH.LOGIN,

            credentials

        );

        const { token, message, ...user } = response.data;

        storage.setToken(token);

        storage.setUser(user);

        return {
            message,
            token,
            user
        };

    },

    logout() {

        storage.clear();

    }

};

export default authService;