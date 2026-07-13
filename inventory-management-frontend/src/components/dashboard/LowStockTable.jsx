const LowStockTable = ({products}) => {


    return (

        <div className="dashboard-card">


            <h3>
                Low Stock Products
            </h3>



            <div className="table-wrapper">


                <table>


                    <thead>

                        <tr>

                            <th>
                                Product
                            </th>

                            <th>
                                Category
                            </th>

                            <th>
                                Stock
                            </th>

                        </tr>


                    </thead>



                    <tbody>


                    {
                        products.length === 0 ? (

                            <tr>

                                <td colSpan="3">

                                    Stock level is good

                                </td>

                            </tr>


                        ) : (


                            products.map(product => (

                                <tr key={product.productId}>


                                    <td>
                                        {product.productName}
                                    </td>


                                    <td>
                                        {product.categoryName}
                                    </td>


                                    <td className="low-stock">

                                        {product.quantity}

                                    </td>


                                </tr>

                            ))

                        )
                    }


                    </tbody>


                </table>


            </div>


        </div>

    );


};


export default LowStockTable;