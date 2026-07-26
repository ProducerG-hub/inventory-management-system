const ProfitReport = ({ data }) => {


    if(!data || data.length === 0){

        return (

            <div className="empty-report">

                No profit data available

            </div>

        );

    }



    return (

        <div className="report-table-wrapper">


            <table className="report-table">


                <thead>

                    <tr>

                        <th>
                            Product
                        </th>


                        <th>
                            Quantity Sold
                        </th>


                        <th>
                            Revenue
                        </th>


                        <th>
                            Cost
                        </th>


                        <th>
                            Profit
                        </th>


                    </tr>

                </thead>



                <tbody>


                {
                    data.map((item)=>(

                        <tr key={item.productId}>


                            <td>

                                {item.productName}

                            </td>


                            <td>

                                {item.totalQuantitySold}

                            </td>



                            <td>

                                TZS{" "}

                                {
                                    item.totalRevenue
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>

                                TZS{" "}

                                {
                                    item.totalCost
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>

                                <span className="profit-value">

                                    TZS{" "}

                                    {
                                        item.totalProfit
                                        ?.toLocaleString()
                                    }

                                </span>

                            </td>


                        </tr>


                    ))
                }


                </tbody>


            </table>


        </div>

    );

};


export default ProfitReport;