import axiosInstance from "../api/axiosConfig";
import API_ENDPOINTS from "../config/constants/apiEndpoints";


const stockMovementService = {


    async getMovements(params){

        const response =
            await axiosInstance.get(

                API_ENDPOINTS.STOCK_MOVEMENTS.BASE,

                {
                    params
                }

            );


        return response.data;

    },

    async getStats(){

    const response =
        await axiosInstance.get(
            "/stock-movements/stats"
        );

    return response.data;

    },





    async searchMovements(params){


        const response =
            await axiosInstance.get(

                API_ENDPOINTS.STOCK_MOVEMENTS.SEARCH,

                {
                    params
                }

            );


        return response.data;

    }



};


export default stockMovementService;