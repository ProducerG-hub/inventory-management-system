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

    }

};

export default API_ENDPOINTS;