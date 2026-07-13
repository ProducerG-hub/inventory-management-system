import axiosInstance from "../api/axiosConfig";

import API_ENDPOINTS from "../config/constants/apiEndpoints";


const dashboardService = {


    async getDashboard(){


        const response = await axiosInstance.get(

            API_ENDPOINTS.DASHBOARD.GET_DASHBOARD

        );


        return response.data;


    }


};


export default dashboardService;