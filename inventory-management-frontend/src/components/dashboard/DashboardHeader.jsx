import { useAuth } from "../../context/AuthContext";

const DashboardHeader = () => {

    const { user } = useAuth();

    return (

        <div className="dashboard-header">

            <div>

                <h2>

                    Welcome, {user?.fullName}

                </h2>

                <p>

                    Inventory Management Dashboard

                </p>

            </div>

        </div>

    );

};

export default DashboardHeader;