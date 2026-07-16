import axiosInstance from "../api/axiosConfig";

const supplierService = {

    /*
        Used by Product Form dropdown
        Returns active suppliers only
    */
    async getSuppliers() {

        const response = await axiosInstance.get(
            "/suppliers",
            {
                params: {
                    active: true,
                    page: 0,
                    size: 100,
                    sortBy: "supplierId",
                    sortDir: "asc"
                }
            }
        );

        return response.data.content;

    },



    async getActiveSuppliers(params) {

        const response = await axiosInstance.get(
            "/suppliers",
            {
                params: {
                    ...params,
                    active: true
                }
            }
        );

        return response.data;

    },



    async getInactiveSuppliers(params) {

        const response = await axiosInstance.get(
            "/suppliers",
            {
                params: {
                    ...params,
                    active: false
                }
            }
        );

        return response.data;

    },



    async searchSuppliers(params) {

        const response = await axiosInstance.get(
            "/suppliers/search",
            {
                params
            }
        );

        return response.data;

    },



    async createSupplier(data) {

        const response = await axiosInstance.post(
            "/suppliers",
            data
        );

        return response.data;

    },



    async updateSupplier(id, data) {

        const response = await axiosInstance.put(
            `/suppliers/${id}`,
            data
        );

        return response.data;

    },



    async deleteSupplier(id) {

        await axiosInstance.delete(
            `/suppliers/${id}`
        );

    },



    async restoreSupplier(id) {

        await axiosInstance.patch(
            `/suppliers/${id}/restore`
        );

    }

};

export default supplierService;