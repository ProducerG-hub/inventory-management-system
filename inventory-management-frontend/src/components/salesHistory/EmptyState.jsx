import { Receipt } from "react-bootstrap-icons";

const EmptyState = ({ title, message }) => {

    return (

        <div className="empty-state">

            <div className="empty-icon">

                <Receipt size={60} />

            </div>

            <h3>
                {title}
            </h3>

            <p>
                {message}
            </p>

        </div>

    );

};

export default EmptyState;