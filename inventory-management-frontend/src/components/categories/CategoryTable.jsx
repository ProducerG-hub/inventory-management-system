import { useAuth } from "../../context/AuthContext";


const CategoryTable = ({

    categories,

    onEdit,

    onDelete,

    onRestore,

    activeTab

}) => {



    const { user } = useAuth();




    return (


        <div className="categories-card">


            <table>


                <thead>


                    <tr>


                        <th>
                            Category
                        </th>


                        <th>
                            Description
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
                    categories.length === 0 ? (



                        <tr>


                            <td

                                colSpan={
                                    user?.role === "ADMIN"
                                    ?
                                    "4"
                                    :
                                    "3"
                                }

                            >

                                No categories found


                            </td>


                        </tr>



                    ) : (



                        categories.map(category => (



                            <tr key={category.categoryId}>


                                <td>

                                    {category.categoryName}

                                </td>





                                <td>

                                    {category.description || "-"}

                                </td>






                                <td>


                                    <span

                                        className={

                                            category.active

                                            ?

                                            "status-active"

                                            :

                                            "status-inactive"

                                        }

                                    >


                                        {

                                            category.active

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

                                                            onClick={() =>
                                                                onEdit(category)
                                                            }

                                                        >

                                                            Edit


                                                        </button>





                                                        <button

                                                            className="delete-btn"

                                                            onClick={() =>
                                                                onDelete(category)
                                                            }

                                                        >

                                                            Deactivate


                                                        </button>



                                                    </>


                                                )


                                                :


                                                (


                                                    <button

                                                        className="restore-btn"

                                                        onClick={() =>
                                                            onRestore(category)
                                                        }

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



export default CategoryTable;