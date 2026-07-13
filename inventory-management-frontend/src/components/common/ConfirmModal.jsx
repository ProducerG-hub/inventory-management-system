import "./ConfirmModal.css";

const ConfirmModal = ({
    isOpen,
    title,
    message,
    confirmText = "Confirm",
    cancelText = "Cancel",
    onConfirm,
    onCancel
}) => {

    if (!isOpen) return null;

    return (

        <div className="confirm-overlay">

            <div className="confirm-modal">

                <h3>{title}</h3>

                <p>{message}</p>

                <div className="confirm-actions">

                    <button
                        className="cancel-btn"
                        onClick={onCancel}
                    >
                        {cancelText}
                    </button>

                    <button
                        className="delete-btn"
                        onClick={onConfirm}
                    >
                        {confirmText}
                    </button>

                </div>

            </div>

        </div>

    );

};

export default ConfirmModal;