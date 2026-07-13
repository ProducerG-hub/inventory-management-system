import MainLayout from "../layouts/MainLayout";
import PrivateRoute from "./PrivateRoute";

import Dashboard from "../pages/dashboard/Dashboard";
import Products from "../pages/products/Products";
import Categories from "../pages/categories/Categories";
import Suppliers from "../pages/suppliers/Suppliers";
import Users from "../pages/users/Users";
import Reports from "../pages/reports/Reports";
import NotFound from "../pages/error/NotFound";

import ROUTES from "../config/constants/routes";

const appRoutes = [
    {
        path: "/",
        element: (
            <PrivateRoute>
                <MainLayout />
            </PrivateRoute>
        ),
        children: [
            {
                index: true,
                path: ROUTES.DASHBOARD,
                element: <Dashboard />
            },
            {
                path: ROUTES.PRODUCTS,
                element: <Products />
            },
            {
                path: ROUTES.CATEGORIES,
                element: <Categories />
            },
            {
                path: ROUTES.SUPPLIERS,
                element: <Suppliers />
            },
            {
                path: ROUTES.USERS,
                element: <Users />
            },
            {
                path: ROUTES.REPORTS,
                element: <Reports />
            }
        ]
    },
    {
        path: "*",
        element: <NotFound />
    }
];

export default appRoutes;