import PageHeader from "../../components/layout/PageHeader";

const NotFound = () => {
    return (
        <>
            <PageHeader
                title="404 Not Found"
                subtitle="The page you are looking for does not exist."
            />
            <h4>Not Found Content</h4>
        </>
    );
};

export default NotFound;