const API_ENDPOINTS = {

    AUTH: {

        LOGIN: "/auth/login",

        LOGOUT: "/auth/logout"

    },

    DASHBOARD: {

        GET_DASHBOARD: "/dashboard"

    },

    SALES: {


        BASE:"/sales",


        SEARCH:"/sales/search",


        RECEIPT:"/sales/{id}/receipt"

    },

    PRODUCTS: {

        BASE: "/products",

        SEARCH: "/products/search"

    },

    CATEGORIES: {

        BASE: "/categories",

        SEARCH: "/categories/search"

    },

    SUPPLIERS: {

        BASE: "/suppliers",

        SEARCH: "/suppliers/search"

    },

    USERS: {

        BASE: "/users",

        SEARCH: "/users/search"

    },

    STOCK_MOVEMENTS: {

    BASE: "/stock-movements",

    SEARCH: "/stock-movements/search"

    },

    SALES_HISTORY: {

        BASE: "/sales-history",
        SEARCH: "/sales-history/search"
    },

    REPORTS: {
        BASE: "/reports",
        SALES_REPORT: "/reports/sales",
        STOCK_REPORT: "/reports/stock",
        CUSTOMER_REPORT: "/reports/customers",
        PROFIT_REPORT: "/reports/profit"
    },
    
    PROFILE: {

        BASE: "/profile"

    },

    MESSAGES: {
        BASE: "/messages",
        CONVERSATIONS: "/messages/conversations",
        MESSAGES_BY_CONVERSATION: "/messages/conversation/{conversationId}",
        MARK_AS_READ: "/messages/conversation/{conversationId}/read"
    }

};

export default API_ENDPOINTS;