const SalesReport = ({data}) => {


    if(!data || data.length === 0){

        return (

            <div className="empty-report">

                No sales data available

            </div>

        );

    }



    return (

        <div className="report-table-wrapper">


            <table className="report-table">


                <thead>

                    <tr>

                        <th>
                            Invoice
                        </th>


                        <th>
                            Customer
                        </th>


                        <th>
                            Cashier
                        </th>


                        <th>
                            Items
                        </th>


                        <th>
                            Amount
                        </th>


                        <th>
                            Date
                        </th>


                    </tr>

                </thead>



                <tbody>


                {
                    data.map((sale)=>(

                        <tr key={sale.saleId}>


                            <td>
                                #{sale.saleId}
                            </td>


                            <td>
                                {sale.customerName}
                            </td>


                            <td>
                                {sale.cashierName}
                            </td>


                            <td>
                                {sale.totalItems}
                            </td>


                            <td>

                                TZS{" "}
                                {
                                    sale.totalAmount
                                    ?.toLocaleString()
                                }

                            </td>


                            <td>

                                {
                                    new Date(
                                        sale.saleDate
                                    )
                                    .toLocaleDateString()
                                }

                            </td>


                        </tr>


                    ))
                }


                </tbody>


            </table>


        </div>

    );

};


export default SalesReport;