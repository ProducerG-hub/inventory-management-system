import ROLES from "./roles";

const PERMISSIONS = {

    MANAGE_USERS: [ROLES.ADMIN],

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

    VIEW_REPORTS: [
        ROLES.ADMIN,
        ROLES.STAFF
    ]

};

export default PERMISSIONS;