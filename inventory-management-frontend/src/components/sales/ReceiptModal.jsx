const ReceiptModal = ({
    receipt,
    close
}) => {



    const formatMoney = (amount)=>{


        return Number(amount)
            .toLocaleString(
                "en-TZ",
                {
                    minimumFractionDigits:0
                }
            );


    };






    const handlePrint = ()=>{


        window.print();


    };







    return (

        <div className="receipt-overlay">


            <div className="receipt-modal">





                <div className="receipt-header">


                    <h2>
                        MLUE INVENTORY
                    </h2>


                    <p>
                        Sales Receipt
                    </p>


                </div>







                <div className="receipt-details">


                    <div>

                        <span>
                            Receipt No:
                        </span>

                        #{receipt.saleId}

                    </div>



                    <div>

                        <span>
                            Date:
                        </span>

                        {
                            new Date(
                                receipt.saleDate
                            )
                            .toLocaleString()
                        }


                    </div>



                    <div>

                        <span>
                            Customer:
                        </span>

                        {receipt.customerName}


                    </div>



                    <div>

                        <span>
                            Cashier:
                        </span>

                        {receipt.cashier}


                    </div>


                </div>








                <table className="receipt-table">


                    <thead>


                        <tr>


                            <th>
                                Product
                            </th>


                            <th>
                                Qty
                            </th>


                            <th>
                                Price
                            </th>


                            <th>
                                Amount
                            </th>


                        </tr>


                    </thead>






                    <tbody>


                    {

                        receipt.items.map(item=>(


                            <tr

                                key={
                                    item.saleItemId
                                }

                            >


                                <td>

                                    {
                                        item.productName
                                    }

                                </td>



                                <td>

                                    {
                                        item.quantity
                                    }

                                </td>




                                <td>

                                    Tsh {
                                        formatMoney(
                                            item.unitPrice
                                        )
                                    }

                                </td>




                                <td>

                                    Tsh {
                                        formatMoney(
                                            item.subtotal
                                        )
                                    }

                                </td>



                            </tr>


                        ))

                    }


                    </tbody>



                </table>









                <div className="receipt-total">


                    <h3>

                        Total:

                        Tsh {
                            formatMoney(
                                receipt.totalAmount
                            )
                        }


                    </h3>


                </div>









                <div className="receipt-actions">


                    <button

                        onClick={handlePrint}

                    >

                        Print Receipt

                    </button>





                    <button

                        onClick={close}

                    >

                        Close

                    </button>


                </div>





            </div>


        </div>


    );

};


export default ReceiptModal;