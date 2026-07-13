const RecentProductsTable = ({products}) => {


    return (

        <div className="dashboard-card">


            <h3>
                Recent Products
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
                                Quantity
                            </th>


                            <th>
                                Price
                            </th>

                        </tr>

                    </thead>



                    <tbody>


                    {
                        products.length === 0 ? (

                            <tr>

                                <td colSpan="4">

                                    No products available

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


                                    <td>
                                        {product.quantity}
                                    </td>


                                    <td>
                                        {product.sellingPrice}
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


export default RecentProductsTable;