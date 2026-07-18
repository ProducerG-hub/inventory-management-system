import UserForm from "./UserForm";

import "./UserModal.css";


const UserModal = ({

    user,

    onClose,

    onSubmit

}) => {


    return (

        <div className="modal-overlay">


            <div className="user-modal">



                <h3 className="modal-header">


                    {

                        user

                        ?

                        "Edit User"

                        :

                        "Add User"

                    }


                </h3>




                <UserForm


                    user={user}


                    onSubmit={onSubmit}


                    onCancel={onClose}


                />



            </div>


        </div>


    );


};


export default UserModal;