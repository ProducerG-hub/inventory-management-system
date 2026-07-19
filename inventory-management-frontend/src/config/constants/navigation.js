import {
    HouseDoorFill,
    BoxSeam,
    TagsFill,
    Truck,
    PeopleFill,
    FileEarmarkBarGraphFill,
    CartCheck
} from "react-bootstrap-icons";

import ROUTES from "./routes";
import ROLES from "./roles";

const navigation = [

    {
        key: "dashboard",
        label: "Dashboard",
        path: `/${ROUTES.DASHBOARD}`,
        icon: HouseDoorFill,
        roles: [ROLES.ADMIN, ROLES.STAFF]
    },
    
    {
        key: "sales",
        label: "Sales",
        path: `/${ROUTES.SALES}`,
        icon: CartCheck,
        roles: [
            ROLES.ADMIN,
            ROLES.STAFF
        ]
    },

    {
        key: "products",
        label: "Products",
        path: `/${ROUTES.PRODUCTS}`,
        icon: BoxSeam,
        roles: [ROLES.ADMIN, ROLES.STAFF]
    },

    {
        key: "categories",
        label: "Categories",
        path: `/${ROUTES.CATEGORIES}`,
        icon: TagsFill,
        roles: [ROLES.ADMIN, ROLES.STAFF]
    },

    {
        key: "suppliers",
        label: "Suppliers",
        path: `/${ROUTES.SUPPLIERS}`,
        icon: Truck,
        roles: [ROLES.ADMIN, ROLES.STAFF]
    },

    {
        key: "users",
        label: "Users",
        path: `/${ROUTES.USERS}`,
        icon: PeopleFill,
        roles: [ROLES.ADMIN]
    },

    {
        key: "reports",
        label: "Reports",
        path: `/${ROUTES.REPORTS}`,
        icon: FileEarmarkBarGraphFill,
        roles: [ROLES.ADMIN, ROLES.STAFF]
    }

];

export default navigation;