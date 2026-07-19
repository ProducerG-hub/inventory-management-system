import React from "react";


const ReceiptPreview = ({ receipt, onClose }) => {


    if (!receipt) return null;



    const formatCurrency = (amount) => {

        return new Intl.NumberFormat(
            "en-TZ",
            {
                style:"currency",
                currency:"TZS",
                minimumFractionDigits:0
            }
        ).format(amount);

    };




    const handlePrint = () => {

        window.print();

    };




    return (

        <div className="receipt-overlay print-area">


            <div className="receipt-modal">


                {/* HEADER */}

                <div className="receipt-header">


                    <h2>
                        MLUE INVENTORY
                    </h2>


                    <p>
                        Inventory Management System
                    </p>


                    <p>
                        Sales Receipt
                    </p>


                </div>





                {/* SALE INFORMATION */}

                <div className="receipt-details">


                    <div>

                        <span>
                            Receipt No
                        </span>


                        <strong>
                            #{receipt.saleId}
                        </strong>

                    </div>




                    <div>

                        <span>
                            Date
                        </span>


                        <strong>
                            {
                                new Date(
                                    receipt.saleDate
                                )
                                .toLocaleString()
                            }
                        </strong>


                    </div>




                    <div>

                        <span>
                            Customer
                        </span>


                        <strong>
                            {receipt.customerName}
                        </strong>


                    </div>




                    <div>

                        <span>
                            Cashier
                        </span>


                        <strong>
                            {receipt.cashier}
                        </strong>


                    </div>



                </div>








                {/* ITEMS */}

                <table className="receipt-table">


                    <thead>

                        <tr>

                            <th>
                                Item
                            </th>


                            <th>
                                Qty
                            </th>


                            <th>
                                Price
                            </th>


                            <th>
                                Total
                            </th>


                        </tr>


                    </thead>





                    <tbody>


                        {
                            receipt.items?.map(
                                (item,index)=> (

                                    <tr key={index}>


                                        <td>
                                            {item.productName}
                                        </td>



                                        <td>
                                            {item.quantity}
                                        </td>



                                        <td>
                                            {
                                                formatCurrency(
                                                    item.unitPrice
                                                )
                                            }
                                        </td>



                                        <td>
                                            {
                                                formatCurrency(
                                                    item.subtotal
                                                )
                                            }
                                        </td>



                                    </tr>


                                )
                            )
                        }



                    </tbody>



                </table>








                {/* TOTAL */}


                <div className="receipt-total" style={{marginTop:"20px", display:"flex", justifyContent:"space-between"}}>


                    <span>
                        Total Amount: 
                    </span>


                    <strong>

                        {
                            formatCurrency(
                                receipt.totalAmount
                            )
                        }

                    </strong>


                </div>







                {/* FOOTER */}


                <div className="receipt-footer">


                    <p>
                        Thank you for shopping with us!
                    </p>


                    <small>
                        Powered by MLUE Technology
                    </small>


                </div>








                {/* ACTIONS */}


                <div className="receipt-actions no-print" >


                    <button
                        onClick={handlePrint}
                    >

                        Print Receipt

                    </button>



                    <button
                        onClick={onClose}
                    >

                        Close

                    </button>


                </div>




            </div>



        </div>


    );

};


export default ReceiptPreview;