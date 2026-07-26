import axiosInstance from "../api/axiosConfig";

const REPORT_ENDPOINT = "/reports";


const reportService = {


    async getSummary(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/summary`
            );

        return response.data;

    },


    async getSalesReport(filters){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/sales`,
                { params: filters }
            );

        return response.data;

    },


    async getInventoryReport(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/inventory`
            );

        return response.data;

    },


    async getCustomerReport(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/customers`
            );

        return response.data;

    },


    async getProfitReport(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/profit`
            );

        return response.data;

    },


    async getSalesTrend(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/sales-trend`
            );

        return response.data;

    },


    async getTopSellingProducts(){

        const response =
            await axiosInstance.get(
                `${REPORT_ENDPOINT}/top-selling`
            );

        return response.data;

    }


};


export default reportService;