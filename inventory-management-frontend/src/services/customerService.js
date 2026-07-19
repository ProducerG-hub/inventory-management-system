import axiosInstance from "../api/axiosConfig";


const customerService = {


    async getCustomers(params){

        const response = await axiosInstance.get(
            "/customers",
            {
                params
            }
        );

        return response.data;

    },



    async searchCustomers(params){

        const response = await axiosInstance.get(
            "/customers/search",
            {
                params
            }
        );


        return response.data;

    },



    async createCustomer(data){

        const response = await axiosInstance.post(
            "/customers",
            data
        );


        return response.data;

    },


    async getCustomerById(id){

        const response = await axiosInstance.get(
            `/customers/${id}`
        );


        return response.data;

    }


};


export default customerService;