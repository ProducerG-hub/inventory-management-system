import axiosInstance from "../api/axiosConfig";

const saleService={


    async createSale(data){


        const response = await axiosInstance.post(

            "/sales",

            data

        );


        return response.data;


    },




    async getSales(params){

        const response=await axiosInstance.get(

            "/sales",

            {params}

        );

        return response.data;

    },

    async searchSales(params){

        const response=await axiosInstance.get(

            "/sales/search",

            {params}

        );

        return response.data;

    },

    async getReceipt(id){

        const response=await axiosInstance.get(

            `/sales/${id}/receipt`

        );

        return response.data;

    }

};

export default saleService;