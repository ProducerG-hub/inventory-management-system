const AuditTable = ({ auditLogs, onView }) => {

    if (!auditLogs || auditLogs.length === 0) {

        return (
            <div className="audit-empty">
                No audit logs found.
            </div>
        );

    }


    return (

        <div className="audit-table-card">

            <table className="audit-data-table">

                <thead>

                    <tr>
                        <th>ID</th>
                        <th>User</th>
                        <th>Action</th>
                        <th>Entity</th>
                        <th>Description</th>
                        <th>Event</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>

                </thead>


                <tbody>

                    {auditLogs.map((audit) => (

                        <tr key={audit.auditId}>

                            <td className="audit-id-cell">
                                #{audit.auditId}
                            </td>

                            <td>

                                <div className="audit-user-cell">
                                    <strong>
                                        {audit.user?.fullName || "System"}
                                    </strong>

                                    {audit.user?.email && (
                                        <small>
                                            {audit.user.email}
                                        </small>
                                    )}

                                </div>

                            </td>

                            <td className="audit-action-cell">
                                {audit.action}
                            </td>

                            <td className="audit-entity-cell">
                                {audit.entityType}

                                {audit.entityId && (
                                    <small>
                                        #{audit.entityId}
                                    </small>
                                )}

                            </td>

                            <td className="audit-description-cell">
                                {audit.description || "—"}
                            </td>

                            <td className="audit-event-cell">
                                {audit.eventType}
                            </td>

                            <td className="audit-date-cell">
                                {new Date(
                                    audit.createdAt
                                ).toLocaleString()}
                            </td>

                            <td>

                                <button
                                    type="button"
                                    className="audit-view-button"
                                    onClick={() => onView(audit.auditId)}
                                >
                                    View
                                </button>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

};


export default AuditTable;