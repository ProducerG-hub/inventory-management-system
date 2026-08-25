import axiosInstance from "../api/axiosConfig";

const messageService = {

    getConversations: async () => {
        const response = await axiosInstance.get("/conversations");
        return response.data;
    },

    createConversation: async (participantId) => {
        const response = await axiosInstance.post(
            "/conversations",
            { participantId }
        );

        return response.data;
    },

    getMessages: async (conversationId) => {
        const response = await axiosInstance.get(
            `/messages/conversation/${conversationId}`
        );

        return response.data;
    },

    sendMessage: async (conversationId, messageContent) => {
        const response = await axiosInstance.post(
            "/messages",
            {
                conversationId,
                messageContent
            }
        );

        return response.data;
    },

    markAsRead: async (conversationId) => {
        await axiosInstance.patch(
            `/messages/conversation/${conversationId}/read`
        );
    }
};

export default messageService;