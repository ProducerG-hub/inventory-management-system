import React from "react";
import logo from "../../assets/images/mlue_logo(blue).png";

import {
    PrinterFill,
    XCircleFill
} from "react-bootstrap-icons";

const ReceiptPreview = ({ receipt, onClose }) => {

    if (!receipt) return null;

    const formatCurrency = (amount) =>
        new Intl.NumberFormat("en-TZ", {
            style: "currency",
            currency: "TZS",
            minimumFractionDigits: 0
        }).format(amount);

    const formatDate = (date) =>
        new Date(date).toLocaleString("en-GB");

    const handlePrint = () => {
        window.print();
    };

    return (

        <div className="receipt-overlay">

            <div className="receipt-modal">

                {/* ================= HEADER ================= */}

                <div className="receipt-header">

                    <div className="receipt-logo">


                        <div className="logo-placeholder">

                            <img
                                src={logo}
                                alt="MLUE Technology Logo"
                                className="receipt-logo-img"
                            />

                        </div>

                    </div>

                    <h2>MLUE POS SYSTEM</h2>

                    <p>Manage Your Sales Efficiently</p>

                    <small>Sales Receipt</small>

                </div>

                <div className="receipt-divider"></div>

                {/* ================= DETAILS ================= */}

                <div className="receipt-details">

                    <div>

                        <span>Receipt</span>

                        <strong>#{receipt.saleId}</strong>

                    </div>

                    <div>

                        <span>Date</span>

                        <strong>

                            {formatDate(receipt.saleDate)}

                        </strong>

                    </div>

                    <div>

                        <span>Customer</span>

                        <strong>

                            {receipt.customerName}

                        </strong>

                    </div>

                    <div>

                        <span>Cashier</span>

                        <strong>

                            {receipt.cashier}

                        </strong>

                    </div>

                </div>

                <div className="receipt-divider"></div>

                {/* ================= ITEMS ================= */}

                <div className="receipt-items">

                    {receipt.items?.map(item => (

                        <div
                            key={item.productId}
                            className="receipt-item"
                        >

                            <div className="receipt-item-name">

                                {item.productName}

                            </div>

                            <div className="receipt-item-row">

                                <span>

                                    {item.quantity} × {formatCurrency(item.unitPrice)}

                                </span>

                                <strong>

                                    {formatCurrency(item.subtotal)}

                                </strong>

                            </div>

                        </div>

                    ))}

                </div>

                <div className="receipt-divider"></div>

                {/* ================= TOTAL ================= */}

                <div className="receipt-total">

                    <span>Total Amount</span>

                    <strong>

                        {formatCurrency(receipt.totalAmount)}

                    </strong>

                </div>

                <div className="receipt-divider"></div>

                {/* ================= FOOTER ================= */}

                <div className="receipt-footer">

                    <p>

                        Thank you for shopping!

                    </p>

                    <small>

                        Powered by MLUE Technology

                    </small>

                </div>

                {/* ================= ACTIONS ================= */}

                <div className="receipt-actions no-print">

                    <button
                        className="print-btn"
                        onClick={handlePrint}
                    >

                        <PrinterFill />

                        Print

                    </button>

                    <button
                        className="close-btn"
                        onClick={onClose}
                    >

                        <XCircleFill />

                        Close

                    </button>

                </div>

            </div>

        </div>

    );

};

export default ReceiptPreview;