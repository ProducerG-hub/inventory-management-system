import { useEffect, useState } from "react";
import auditService from "../../services/auditService";
import "./AuditDetailModal.css";

const formatJson = (value) => {

    if (!value) {
        return "No data";
    }

    try {
        return JSON.stringify(
            JSON.parse(value),
            null,
            2
        );
    } catch (error) {
        return value;
    }

};


const AuditDetailModal = ({ auditId, onClose }) => {

    const [audit, setAudit] = useState(null);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState(null);


    useEffect(() => {

        const fetchAuditDetail = async () => {

            try {

                setLoading(true);
                setError(null);

                const data = await auditService.getAuditLogById(auditId);

                setAudit(data);

            } catch (error) {

                console.error(
                    "Failed to fetch audit detail:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Failed to load audit log details."
                );

            } finally {

                setLoading(false);

            }

        };


        if (auditId) {

            fetchAuditDetail();

        }

    }, [auditId]);


    if (loading) {

        return (
            <div className="audit-detail-overlay">
                <div className="audit-detail-state audit-detail-loading" role="dialog" aria-modal="true" aria-label="Loading audit details">
                    <span className="audit-detail-spinner" aria-hidden="true" />
                    <p>Loading audit details...</p>
                </div>
            </div>
        );

    }


    if (error) {

        return (
            <div className="audit-detail-overlay">
                <div className="audit-detail-state audit-detail-error" role="dialog" aria-modal="true" aria-label="Audit detail error">
                    <p>{error}</p>

                    <button type="button" onClick={onClose}>
                        Close
                    </button>
                </div>
            </div>
        );

    }


    if (!audit) {

        return null;

    }


    return (
    <div className="audit-detail-overlay">

        <div
            className="audit-detail-modal"
            role="dialog"
            aria-modal="true"
            aria-labelledby="audit-detail-title"
        >

            <div className="audit-detail-header">

                <div>
                    <span className="audit-detail-eyebrow">Activity record</span>
                    <h2 id="audit-detail-title">
                        Audit Log #{audit.auditId}
                    </h2>
                </div>

                <button
                    type="button"
                    className="audit-detail-close"
                    onClick={onClose}
                    aria-label="Close audit details"
                >
                    ×
                </button>

            </div>


            <div className="audit-detail-body">

                <div className="audit-detail-grid">

                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            User
                        </span>

                        <span className="audit-detail-value">
                            {audit.user?.fullName || "System"}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            Email
                        </span>

                        <span className="audit-detail-value">
                            {audit.user?.email || "—"}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            Action
                        </span>

                        <span className="audit-detail-value audit-detail-value-emphasis">
                            {audit.action}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            Entity
                        </span>

                        <span className="audit-detail-value audit-detail-value-emphasis">
                            {audit.entityType}
                            {audit.entityId && ` #${audit.entityId}`}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            Event
                        </span>

                        <span className="audit-detail-value audit-detail-value-emphasis">
                            {audit.eventType}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            IP Address
                        </span>

                        <span className="audit-detail-value">
                            {audit.ipAddress || "—"}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            Created At
                        </span>

                        <span className="audit-detail-value">
                            {audit.createdAt
                                ? new Date(
                                    audit.createdAt
                                ).toLocaleString()
                                : "—"}
                        </span>
                    </div>


                    <div className="audit-detail-item">
                        <span className="audit-detail-label">
                            User Agent
                        </span>

                        <span className="audit-detail-value">
                            {audit.userAgent || "—"}
                        </span>
                    </div>

                </div>


                <div className="audit-detail-description audit-detail-item">

                    <span className="audit-detail-label">
                        Description
                    </span>

                    <span className="audit-detail-value">
                        {audit.description || "—"}
                    </span>

                </div>


                <div className="audit-detail-section audit-detail-section-old">

                    <div className="audit-detail-section-heading">
                        <span className="audit-detail-section-marker">Before</span>
                        <h3>Old Values</h3>
                    </div>

                    <pre className="audit-detail-code">
                        {formatJson(audit.oldValues)}
                    </pre>

                </div>

                <div className="audit-detail-section audit-detail-section-new">

                    <div className="audit-detail-section-heading">
                        <span className="audit-detail-section-marker">After</span>
                        <h3>New Values</h3>
                    </div>

                    <pre className="audit-detail-code">
                        {formatJson(audit.newValues)}
                    </pre>

                </div>
            </div>

        </div>

    </div>
);

};


export default AuditDetailModal;