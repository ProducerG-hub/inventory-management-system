import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import ROUTES from "../config/constants/routes";


const PrivateRoute = ({ children }) => {


    const { isAuthenticated } = useAuth();



    if (!isAuthenticated) {

        return <Navigate to={ROUTES.LOGIN} replace />;

    }


    return children;

};


export default PrivateRoute;