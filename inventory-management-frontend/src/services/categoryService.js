import axiosInstance from "../api/axiosConfig";


const categoryService = {

    async getCategories(){

        const response = await axiosInstance.get(
            "/categories",
            {
                params:{
                    page:0,
                    size:100,
                    sortBy:"categoryId",
                    sortDir:"asc"
                }
            }
        );


        return response.data.content;


    }


};


export default categoryService;