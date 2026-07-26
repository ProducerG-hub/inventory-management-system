const CustomerReport = ({ data }) => {


    if(!data || data.length === 0){

        return (

            <div className="empty-report">

                No customer data available

            </div>

        );

    }



    return (

        <div className="report-table-wrapper">


            <table className="report-table">


                <thead>

                    <tr>

                        <th>
                            Customer
                        </th>


                        <th>
                            Phone
                        </th>


                        <th>
                            Total Orders
                        </th>


                        <th>
                            Total Spent
                        </th>


                        <th>
                            Last Purchase
                        </th>


                    </tr>

                </thead>



                <tbody>


                {
                    data.map((customer)=>(

                        <tr key={customer.customerId}>


                            <td>

                                {customer.customerName}

                            </td>


                            <td>

                                {customer.phoneNumber}

                            </td>


                            <td>

                                {customer.totalOrders}

                            </td>



                            <td>

                                TZS{" "}

                                {
                                    customer.totalSpent
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>

                                {
                                    customer.lastPurchaseDate
                                    ?
                                    new Date(
                                        customer.lastPurchaseDate
                                    )
                                    .toLocaleDateString()
                                    :
                                    "No purchase"
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


export default CustomerReport;