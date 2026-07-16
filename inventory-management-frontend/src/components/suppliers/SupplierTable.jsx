import { useAuth } from "../../context/AuthContext";


const SupplierTable = ({

    suppliers,

    onEdit,

    onDelete,

    onRestore,

    activeTab

}) => {


    const { user } = useAuth();



    return (


        <div className="suppliers-card">


            <table>


                <thead>


                    <tr>


                        <th>
                            Supplier
                        </th>


                        <th>
                            Company
                        </th>


                        <th>
                            Phone
                        </th>


                        <th>
                            Email
                        </th>


                        <th>
                            Location
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
                    suppliers.length === 0 ? (


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

                                No suppliers found


                            </td>


                        </tr>



                    ) : (



                        suppliers.map((supplier)=>(



                            <tr key={supplier.supplierId}>


                                <td>

                                    {supplier.supplierName}

                                </td>




                                <td>

                                    {supplier.companyName || "-"}

                                </td>




                                <td>

                                    {supplier.phone || "-"}

                                </td>




                                <td>

                                    {supplier.email || "-"}

                                </td>




                                <td>

                                    {
                                        supplier.street
                                    }

                                    {
                                        supplier.district
                                        &&
                                        `, ${supplier.district}`
                                    }

                                </td>




                                <td>


                                    <span

                                        className={

                                            supplier.active

                                            ?

                                            "status-active"

                                            :

                                            "status-inactive"

                                        }

                                    >


                                        {
                                            supplier.active

                                            ?

                                            "Active"

                                            :

                                            "Inactive"
                                        }


                                    </span>


                                </td>






                                {
                                    user?.role === "ADMIN" && (


                                        <td className="actions">


                                        {
                                            activeTab === "active"

                                            ?

                                            (

                                                <>


                                                <button

                                                    className="edit-btn"

                                                    onClick={()=>onEdit(supplier)}

                                                >

                                                    Edit

                                                </button>





                                                <button

                                                    className="delete-btn"

                                                    onClick={()=>onDelete(supplier)}

                                                >

                                                    Deactivate

                                                </button>



                                                </>

                                            )


                                            :

                                            (


                                                <button

                                                    className="restore-btn"

                                                    onClick={()=>onRestore(supplier)}

                                                >

                                                    Restore

                                                </button>


                                            )

                                        }



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


export default SupplierTable;