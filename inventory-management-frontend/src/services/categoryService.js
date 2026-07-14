import axiosInstance from "../api/axiosConfig";


const categoryService = {

    // Used by Product dropdown
    async getCategories(){

        const response = await axiosInstance.get(
            "/categories",
            {
                params:{
                    active:true,
                    page:0,
                    size:100,
                    sortBy:"categoryId",
                    sortDir:"asc"
                }
            }
        );


        return response.data.content;

    },

    // Active / Inactive listing

    async getAllCategories(params){

        const response = await axiosInstance.get(
            "/categories",
            {
                params
            }
        );


        return response.data;

    },

    // Search

    async searchCategories(params){

        const response = await axiosInstance.get(
            "/categories/search",
            {
                params
            }
        );


        return response.data;

    },

    // Create

    async createCategory(data){

        const response = await axiosInstance.post(
            "/categories",
            data
        );


        return response.data;

    },

    // Update

    async updateCategory(id,data){

        const response = await axiosInstance.put(
            `/categories/${id}`,
            data
        );


        return response.data;

    },

    // Soft delete

    async deleteCategory(id){

        await axiosInstance.delete(
            `/categories/${id}`
        );

    },


    // Restore

    async restoreCategory(id){

        await axiosInstance.patch(
            `/categories/${id}/restore`
        );

    }



};


export default categoryService;