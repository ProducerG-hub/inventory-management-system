import axiosInstance from "../api/axiosConfig";

const userService = {

    async getActiveUsers(params){

        const response = await axiosInstance.get(
            "/users",
            {
                params: {
                    ...params,
                    active: true
                }
            }
        );

        return response.data;
    },



    async getInactiveUsers(params){

        const response = await axiosInstance.get(
            "/users",
            {
                params: {
                    ...params,
                    active: false
                }
            }
        );

        return response.data;
    },



    async searchUsers(params){

        const response = await axiosInstance.get(
            "/users/search",
            {
                params
            }
        );

        return response.data;
    },



    async createUser(data){

        const response = await axiosInstance.post(
            "/users",
            data
        );

        return response.data;
    },



    async updateUser(id, data){

        const response = await axiosInstance.put(
            `/users/${id}`,
            data
        );

        return response.data;
    },



    async deleteUser(id){

        await axiosInstance.delete(
            `/users/${id}`
        );

    },



    async restoreUser(id){

        await axiosInstance.patch(
            `/users/${id}/restore`
        );

    }

};

export default userService;