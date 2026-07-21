import ROLES from "./roles";

const PERMISSIONS = {

    MANAGE_USERS: [ROLES.ADMIN],

    MANAGE_SALES: [
        ROLES.ADMIN,
        ROLES.STAFF
    ],

    MANAGE_PRODUCTS: [
        ROLES.ADMIN,
        ROLES.STAFF
    ],

    MANAGE_CATEGORIES: [
        ROLES.ADMIN,
        ROLES.STAFF
    ],

    MANAGE_SUPPLIERS: [
        ROLES.ADMIN,
        ROLES.STAFF
    ],

    VIEW_SALES_HISTORY:[
        ROLES.ADMIN
    ],

    VIEW_REPORTS: [
        ROLES.ADMIN
    ]

};

export default PERMISSIONS;