import { Link } from "react-router-dom";
import "./NotFound.css";

const NotFound = () => {
    return (
        <div className="not-found-page">
            <div className="not-found-card">
                <div className="not-found-code">404</div>
                <h4 className="not-found-title">Page not found</h4>
                <p className="not-found-text">
                    The page you are looking for does not exist or may have been moved.
                </p>
                <Link to="/dashboard" className="not-found-btn">
                    Back to dashboard
                </Link>
            </div>
        </div>
    );
};

export default NotFound;