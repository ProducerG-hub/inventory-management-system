import { useRoutes } from "react-router-dom";

import authRoutes from "./authRoutes";
import appRoutes from "./appRoutes";

const AppRouter = () => {

    const routes = [...authRoutes, ...appRoutes];

    return useRoutes(routes);

};

export default AppRouter;