import { useEffect, useState } from "react";
import auditService from "../../services/auditService";
import AuditTable from "../../components/auditTrail/AuditTable";
import AuditPagination from "../../components/auditTrail/AuditPagination";
import AuditDetailModal from "../../components/auditTrail/AuditDetailModal";
import "./AuditTrail.css";


const AuditTrail = () => {

    const [auditLogs, setAuditLogs] = useState([]);

    const [loading, setLoading] = useState(true);

    const [selectedAuditId, setSelectedAuditId] = useState(null);

    const [error, setError] = useState(null);

    const [pagination, setPagination] = useState({
        page: 0,
        size: 10,
        totalPages: 0,
        totalElements: 0
    });


    const fetchAuditLogs = async () => {

        try {

            setLoading(true);
            setError(null);

            const data = await auditService.getAuditLogs({
                page: pagination.page,
                size: pagination.size,
                sortBy: "createdAt",
                sortDir: "desc"
            });

            setAuditLogs(data.content);

            setPagination(prev => ({
                ...prev,
                totalPages: data.totalPages,
                totalElements: data.totalElements
            }));

        } catch (error) {

            console.error("Failed to fetch audit logs:", error);

            setError(
                error.response?.data?.message ||
                "Failed to load audit logs."
            );

        } finally {

            setLoading(false);

        }

    };


    useEffect(() => {

        fetchAuditLogs();

    }, [pagination.page, pagination.size]);


    if (loading) {

        return (
            <div className="audit-trail-page audit-state-card">
                <p>Loading audit logs...</p>
            </div>
        );

    }


    if (error) {

        return (
            <div className="audit-trail-page audit-state-card audit-state-error">
                <p>{error}</p>
            </div>
        );

    }


    return (

        <div className="audit-trail-page">

            <div className="audit-trail-header">
                <div>
                    <h1>Audit Logs</h1>

                    <p>
                        Review system activity and changes across the inventory.
                    </p>
                </div>

                <div className="audit-trail-count">
                    <strong>{pagination.totalElements}</strong>
                    <span>Total records</span>
                </div>
            </div>

            <AuditTable
                auditLogs={auditLogs}
                onView={(id) => setSelectedAuditId(id)}
            />

            <AuditPagination
            currentPage={pagination.page}
            totalPages={pagination.totalPages}
            setPage={(page) =>
                setPagination(prev => ({
                    ...prev,
                    page
                }))
            }
            />
            
            {selectedAuditId && (

                <AuditDetailModal
                    auditId={selectedAuditId}
                    onClose={() => setSelectedAuditId(null)}
                />
            )}
        </div>

    );

};


export default AuditTrail;