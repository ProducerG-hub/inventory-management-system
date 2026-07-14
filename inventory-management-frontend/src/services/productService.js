import axiosInstance from "../api/axiosConfig";


const productService = {


    async getProducts(params){

        const response = await axiosInstance.get(
            "/products",
            {
                params
            }
        );

        return response.data;

    },



    async searchProducts(params){

        const response = await axiosInstance.get(
            "/products/search",
            {
                params
            }
        );

        return response.data;

    },



    async createProduct(data){

        const response = await axiosInstance.post(
            "/products",
            data
        );

        return response.data;

    },



    async updateProduct(id,data){

        const response = await axiosInstance.put(

            `/products/${id}`,

            data

        );

        return response.data;

    },



    async deleteProduct(id){

        await axiosInstance.delete(

            `/products/${id}`

        );

    },

    async getActiveProducts(params){

        const response = await axiosInstance.get(
            "/products/active",
            {params}
        );

        return response.data;

    },

    async getInactiveProducts(params){

    const response = await axiosInstance.get(
        "/products/inactive",
        {params}
    );

    return response.data;

    },

    async restoreProduct(id){

    await axiosInstance.patch(
        `/products/${id}/restore`
    );

}

};


export default productService;