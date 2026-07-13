import { useAuth } from "../../context/AuthContext";


const ProductTable = ({

    products,

    onEdit,

    onDelete

}) => {



    const { user } = useAuth();




    return (


        <div className="products-card">



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
                            Supplier
                        </th>


                        <th>
                            Price
                        </th>


                        <th>
                            Stock
                        </th>


                        <th>
                            Status
                        </th>



                        {
                            user?.role === "ADMIN" && (

                                <th>
                                    Actions
                                </th>

                            )
                        }


                    </tr>


                </thead>





                <tbody>



                {
                    products.length === 0 ? (


                        <tr>


                            <td

                                colSpan={
                                    user?.role === "ADMIN"
                                    ?
                                    "7"
                                    :
                                    "6"
                                }

                            >

                                No products found


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

                                    {product.supplierName}

                                </td>




                                <td>

                                    Tsh {Number(

                                        product.sellingPrice

                                    ).toLocaleString()}


                                </td>




                                <td>

                                    {product.quantity}

                                </td>





                                <td>



                                    <span

                                        className={

                                            product.isActive

                                            ?

                                            "status-active"

                                            :

                                            "status-inactive"

                                        }

                                    >


                                        {

                                            product.isActive

                                            ?

                                            "Active"

                                            :

                                            "Inactive"

                                        }


                                    </span>



                                </td>






                                {
                                    user?.role === "ADMIN" && (


                                        <td>


                                            <button


                                                className="edit-btn"


                                                onClick={() =>

                                                    onEdit(product)

                                                }

                                            >

                                                Edit


                                            </button>





                                            <button


                                                className="delete-btn"


                                                onClick={() =>

                                                    onDelete(

                                                        product

                                                    )

                                                }


                                            >

                                                Delete


                                            </button>



                                        </td>



                                    )
                                }




                            </tr>




                        ))



                    )
                }



                </tbody>



            </table>




        </div>


    );



};



export default ProductTable;