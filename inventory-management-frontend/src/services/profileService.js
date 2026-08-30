import axiosInstance from "../api/axiosConfig";

const profileService = {

    async getMyProfile() {

        const response = await axiosInstance.get(
            "/profile/me"
        );

        return response.data;
    },


    async updateMyProfile(data) {

        const response = await axiosInstance.put(
            "/profile/me",
            data
        );

        return response.data;
    },


    async changePassword(data) {

        await axiosInstance.put(
            "/profile/change-password",
            data
        );

    },


    async uploadProfilePicture(file) {

    const formData = new FormData();

    formData.append("file", file);

    const response = await axiosInstance.post(
        "/profile/me/picture",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );

    return response.data;
},

    async deleteProfilePicture() {

        await axiosInstance.delete(
            "/profile/me/picture"
        );

    }

};

export default profileService;