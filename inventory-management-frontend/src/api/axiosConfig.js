import axios from "axios";
import API from "../config/api";
import { storage } from "../utils/authStorage";

const axiosInstance = axios.create({
    baseURL: API.BASE_URL,
    timeout: API.TIMEOUT,
    headers: {
        "Content-Type": "application/json",
    },
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = storage.getToken();

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

export default axiosInstance;