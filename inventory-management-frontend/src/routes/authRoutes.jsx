import Login from "../pages/auth/Login";
import ROUTES from "../config/constants/routes";

const authRoutes = [
    {
        path: ROUTES.LOGIN,
        element: <Login />
    }
];

export default authRoutes;