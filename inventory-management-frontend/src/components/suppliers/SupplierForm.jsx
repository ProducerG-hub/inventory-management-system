import { useEffect, useState } from "react";

import "./SupplierModal.css";


const SupplierForm = ({
    supplier,
    onSubmit,
    onCancel
}) => {



    const initialState = {

        supplierName: "",

        companyName: "",

        phone: "",

        email: "",

        street: "",

        district: ""

    };





    const [form,setForm] = useState(initialState);







    /*
        Populate form when editing
    */

    useEffect(()=>{


        if(supplier){


            setForm({

                supplierName: supplier.supplierName || "",

                companyName: supplier.companyName || "",

                phone: supplier.phone || "",

                email: supplier.email || "",

                street: supplier.street || "",

                district: supplier.district || ""

            });



        }else{


            setForm(initialState);


        }



    },[supplier]);









    const handleChange = (e)=>{


        const {
            name,
            value
        } = e.target;



        setForm({

            ...form,

            [name]: value

        });


    };









    const handleSubmit = (e)=>{


        e.preventDefault();




        const requestData = {


            supplierName: form.supplierName,


            companyName: form.companyName,


            phone: form.phone,


            email: form.email,


            street: form.street,


            district: form.district


        };





        console.log(
            "Supplier Payload:",
            requestData
        );





        onSubmit(requestData);



    };









    return (


        <form

            className="supplier-form"

            onSubmit={handleSubmit}

        >



            <div className="supplier-form-grid">






                <input

                    type="text"

                    name="supplierName"

                    placeholder="Supplier Name"

                    value={form.supplierName}

                    onChange={handleChange}

                    required

                />







                <input

                    type="text"

                    name="companyName"

                    placeholder="Company Name"

                    value={form.companyName}

                    onChange={handleChange}

                />







                <input

                    type="text"

                    name="phone"

                    placeholder="Phone Number"

                    value={form.phone}

                    onChange={handleChange}

                />








                <input

                    type="email"

                    name="email"

                    placeholder="Email Address"

                    value={form.email}

                    onChange={handleChange}

                />









                <input

                    type="text"

                    name="street"

                    placeholder="Street"

                    value={form.street}

                    onChange={handleChange}

                />









                <input

                    type="text"

                    name="district"

                    placeholder="District"

                    value={form.district}

                    onChange={handleChange}

                />







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


export default SupplierForm;