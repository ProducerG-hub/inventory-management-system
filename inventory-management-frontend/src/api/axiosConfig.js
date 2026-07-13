import axios from "axios";

import API from "../api/api";
import storage from "../utils/authStorage";

import toast from "react-hot-toast";


const axiosInstance = axios.create({

    baseURL: API.BASE_URL,

    timeout: API.TIMEOUT,

    headers: {
        "Content-Type": "application/json",
    },

});



/*
    REQUEST INTERCEPTOR
    Attach JWT Token automatically
*/

axiosInstance.interceptors.request.use(

    (config) => {


        const token = storage.getToken();


        if (token) {

            config.headers.Authorization =
                `Bearer ${token}`;

        }


        return config;

    },


    (error) => {

        return Promise.reject(error);

    }

);




/*
    RESPONSE INTERCEPTOR
    Global API Error Handling
*/

axiosInstance.interceptors.response.use(


    (response) => {

        return response;

    },


    (error) => {


        if (!error.response) {


            toast.error(
                "Network error. Please check your connection."
            );


            return Promise.reject(error);

        }



        const status = error.response.status;



        switch(status) {


            case 401:

                storage.clear();


                toast.error(
                    "Session expired. Please login again."
                );


                window.location.href = "/login";


                break;



            case 403:


                toast.error(
                    "You don't have permission to perform this action."
                );


                break;



            case 500:


                toast.error(
                    "Server error. Please try again later."
                );


                break;



            default:


                toast.error(
                    error.response.data?.message ||
                    "Something went wrong."
                );


        }



        return Promise.reject(error);


    }

);



export default axiosInstance;