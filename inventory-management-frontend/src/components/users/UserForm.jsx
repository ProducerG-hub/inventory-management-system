import { useEffect, useState } from "react";

import "./UserModal.css";


const UserForm = ({

    user,

    onSubmit,

    onCancel

}) => {


    const initialState = {

        fullName: "",

        email: "",

        password: "",

        role: "STAFF",

        isActive: true

    };



    const [form, setForm] = useState(initialState);




    /*
        Populate form during edit
    */

    useEffect(() => {


        if(user){


            setForm({

                fullName: user.fullName,

                email: user.email,

                password: "",

                role: user.role,

                isActive: user.isActive

            });



        }else{


            setForm(initialState);


        }



    }, [user]);







    const handleChange = (e) => {


        const {

            name,

            value

        } = e.target;



        setForm({

            ...form,

            [name]: value

        });


    };







    const handleSubmit = (e) => {


        e.preventDefault();




        const requestData = {


            fullName: form.fullName,


            email: form.email,


            role: form.role,


            isActive: form.isActive



        };





        /*
            Only send password if provided

            Useful during update
        */


        if(form.password.trim()){


            requestData.password = form.password;


        }





        console.log(

            "User Payload:",

            requestData

        );




        onSubmit(requestData);


    };








    return (


        <form

            className="user-form"

            onSubmit={handleSubmit}

        >




            <div className="user-form-grid">





                <input


                    type="text"


                    name="fullName"


                    placeholder="Full Name"


                    value={form.fullName}


                    onChange={handleChange}


                    required


                />







                <input


                    type="email"


                    name="email"


                    placeholder="Email"


                    value={form.email}


                    onChange={handleChange}


                    required


                />







                <input


                    type="password"


                    name="password"


                    placeholder={

                        user

                        ?

                        "Leave blank to keep current password"

                        :

                        "Password"

                    }


                    value={form.password}


                    onChange={handleChange}


                    required={!user}


                />









                <select


                    name="role"


                    value={form.role}


                    onChange={handleChange}



                >


                    <option value="ADMIN">

                        ADMIN

                    </option>



                    <option value="STAFF">

                        STAFF

                    </option>



                </select>









                <select


                    name="isActive"


                    value={form.isActive}


                    onChange={(e)=>


                        setForm({

                            ...form,

                            isActive:

                            e.target.value === "true"

                        })


                    }



                >


                    <option value="true">

                        Active

                    </option>




                    <option value="false">

                        Inactive

                    </option>



                </select>




            </div>








            <div className="form-actions">





                <button


                    type="submit"


                    className="save-btn"


                >


                    Save


                </button>







                <button


                    type="button"


                    className="cancel-btn"


                    onClick={onCancel}


                >


                    Cancel


                </button>




            </div>






        </form>


    );


};


export default UserForm;