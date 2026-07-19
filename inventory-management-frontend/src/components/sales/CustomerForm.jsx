import { useState } from "react";

import customerService from "../../services/customerService";


const CustomerForm = ({
    close,
    onCreated
})=>{


    const [form,setForm]=useState({

        customerName:"",
        phone:"",
        email:"",
        street:"",
        district:""

    });


    const [loading,setLoading]=useState(false);







    const handleChange=(e)=>{


        setForm({

            ...form,

            [e.target.name]:e.target.value

        });


    };








    const submit=async(e)=>{


        e.preventDefault();


        try{


            setLoading(true);



            const response =
                await customerService.createCustomer(form);



            onCreated(response);



        }
        catch(error){

            console.error(error);

        }
        finally{

            setLoading(false);

        }


    };







    return (

        <div className="customer-modal">


            <form

                className="customer-form"

                onSubmit={submit}

            >


                <h3>
                    New Customer
                </h3>



                <input

                    name="customerName"

                    placeholder="Customer name"

                    value={form.customerName}

                    onChange={handleChange}

                    required

                />



                <input

                    name="phone"

                    placeholder="Phone"

                    value={form.phone}

                    onChange={handleChange}

                />



                <input

                    name="email"

                    placeholder="Email"

                    value={form.email}

                    onChange={handleChange}

                />



                <input

                    name="street"

                    placeholder="Street"

                    value={form.street}

                    onChange={handleChange}

                />



                <input

                    name="district"

                    placeholder="District"

                    value={form.district}

                    onChange={handleChange}

                />




                <div>


                    <button

                        type="button"

                        onClick={close}

                    >

                        Cancel

                    </button>



                    <button

                        disabled={loading}

                    >

                        {
                            loading
                            ?
                            "Saving..."
                            :
                            "Save"
                        }


                    </button>


                </div>



            </form>


        </div>

    );


};


export default CustomerForm;