const InventoryReport = ({ data }) => {


    if(!data || data.length === 0){

        return (

            <div className="empty-report">

                No inventory data available

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
                            Category
                        </th>


                        <th>
                            Supplier
                        </th>


                        <th>
                            Buying Price
                        </th>


                        <th>
                            Selling Price
                        </th>


                        <th>
                            Quantity
                        </th>


                        <th>
                            Stock Value
                        </th>


                        <th>
                            Status
                        </th>


                    </tr>

                </thead>



                <tbody>


                {
                    data.map((product)=>(

                        <tr key={product.productId}>


                            <td>
                                {product.productName}
                            </td>


                            <td>
                                {product.categoryName}
                            </td>


                            <td>
                                {product.supplierName}
                            </td>



                            <td>

                                TZS{" "}
                                {
                                    product.buyingPrice
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>

                                TZS{" "}
                                {
                                    product.sellingPrice
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>

                                {product.quantity}

                            </td>



                            <td>

                                TZS{" "}
                                {
                                    product.stockValue
                                    ?.toLocaleString()
                                }

                            </td>



                            <td>


                                <span

                                    className={
                                        product.stockStatus === "Low Stock"
                                        ?
                                        "status-danger"
                                        :
                                        "status-success"
                                    }

                                >

                                    {product.stockStatus}

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


export default InventoryReport;