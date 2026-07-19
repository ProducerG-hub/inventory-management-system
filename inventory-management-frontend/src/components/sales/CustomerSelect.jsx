import { useEffect, useState } from "react";

import customerService from "../../services/customerService";
import CustomerForm from "./CustomerForm";


const CustomerSelect = ({
    customer,
    setCustomer
}) => {


    const [keyword,setKeyword] = useState("");

    const [customers,setCustomers] = useState([]);

    const [showForm,setShowForm] = useState(false);

    const [loading,setLoading] = useState(false);





    useEffect(()=>{


        const timer = setTimeout(()=>{


            if(keyword.trim()){

                searchCustomers();

            }
            else{

                setCustomers([]);

            }


        },500);



        return ()=>clearTimeout(timer);



    },[keyword]);







    const searchCustomers = async()=>{


        try{


            setLoading(true);


            const response =
                await customerService.searchCustomers({

                    keyword,

                    page:0,

                    size:10

                });



            setCustomers(
                response.content
            );



        }
        catch(error){

            console.error(error);

        }
        finally{

            setLoading(false);

        }


    };







    const selectCustomer=(selected)=>{


        setCustomer(selected);

        setKeyword("");

        setCustomers([]);


    };








    return (

        <div className="customer-select">


            <h3>
                Customer
            </h3>




            {
                customer ?

                (

                    <div className="selected-customer">


                        <strong>
                            {customer.customerName}
                        </strong>


                        <p>
                            {customer.phone}
                        </p>



                        <button

                            onClick={()=>{
                                setCustomer(null)
                            }}

                        >

                            Change Customer

                        </button>


                    </div>


                )


                :


                (

                    <>

                    <input

                        placeholder="Search customer..."

                        value={keyword}

                        onChange={
                            e=>setKeyword(e.target.value)
                        }

                    />



                    {
                        loading &&
                        <p>
                            Searching...
                        </p>
                    }



                    <div className="customer-results">


                    {
                        customers.map(item=>(


                            <div

                                key={item.customerId}

                                className="customer-item"

                                onClick={()=>
                                    selectCustomer(item)
                                }

                            >

                                <strong>
                                    {item.customerName}
                                </strong>


                                <small>
                                    {item.phone}
                                </small>


                            </div>


                        ))

                    }


                    </div>





                    <button

                        onClick={()=>
                            setShowForm(true)
                        }

                    >

                        + New Customer

                    </button>


                    </>

                )

            }





            {
                showForm &&


                <CustomerForm

                    close={()=>
                        setShowForm(false)
                    }


                    onCreated={(newCustomer)=>{


                        setCustomer(newCustomer);

                        setShowForm(false);


                    }}

                />


            }



        </div>

    );


};


export default CustomerSelect;