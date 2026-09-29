import axiosInstance from "../api/axiosConfig";
import API_ENDPOINTS from "../config/constants/apiEndpoints";


const auditService = {
    async getAuditLogs(params) {
        const response = await axiosInstance.get(
            API_ENDPOINTS.AUDIT_LOGS.BASE,
            {
                params
            }
        );
        return response.data;

    },


    async getAuditLogById(id) {
        const url = API_ENDPOINTS.AUDIT_LOGS.BY_ID.replace(
            "{id}",
            id
        );
        const response = await axiosInstance.get(url);
        return response.data;

    }

};


export default auditService;