import { useAuth } from "../../context/AuthContext";


const UserTable = ({

    users,

    activeTab,

    onEdit,

    onDelete,

    onRestore

}) => {


    const { user } = useAuth();



    return (

        <div className="users-card">


            <table>


                <thead>

                    <tr>


                        <th>
                            Full Name
                        </th>


                        <th>
                            Email
                        </th>


                        <th>
                            Role
                        </th>


                        <th>
                            Status
                        </th>


                        <th>
                            Created At
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

                    users.length === 0 ? (


                        <tr>


                            <td

                                colSpan={
                                    user?.role === "ADMIN"
                                    ?
                                    "6"
                                    :
                                    "5"
                                }

                            >

                                No users found


                            </td>


                        </tr>



                    ) : (



                        users.map((item) => (


                            <tr key={item.userId}>


                                <td>

                                    {item.fullName}

                                </td>



                                <td>

                                    {item.email}

                                </td>




                                <td>


                                    <span

                                        className={
                                            item.role === "ADMIN"

                                            ?

                                            "role-admin"

                                            :

                                            "role-staff"
                                        }

                                    >

                                        {item.role}

                                    </span>


                                </td>





                                <td>


                                    <span

                                        className={

                                            item.isActive

                                            ?

                                            "status-active"

                                            :

                                            "status-inactive"

                                        }

                                    >


                                        {

                                            item.isActive

                                            ?

                                            "Active"

                                            :

                                            "Inactive"

                                        }


                                    </span>


                                </td>






                                <td>


                                    {

                                        item.createdAt

                                        ?

                                        new Date(

                                            item.createdAt

                                        ).toLocaleDateString()

                                        :

                                        "-"

                                    }


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

                                                            onEdit(item)

                                                        }

                                                    >

                                                        Edit

                                                    </button>



                                                    <button

                                                        className="delete-btn"

                                                        onClick={() =>

                                                            onDelete(item)

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

                                                        onRestore(item)

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


export default UserTable;