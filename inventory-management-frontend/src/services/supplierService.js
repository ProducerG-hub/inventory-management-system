import axiosInstance from "../api/axiosConfig";


const supplierService = {


    async getSuppliers(){


        const response = await axiosInstance.get(
            "/suppliers",
            {
                params:{
                    page:0,
                    size:100,
                    sortBy:"supplierId",
                    sortDir:"asc"
                }
            }
        );




        return response.data.content;


    }


};


export default supplierService;