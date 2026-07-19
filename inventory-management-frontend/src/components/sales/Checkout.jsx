import { useState } from "react";

import saleService from "../../services/saleService";


const Checkout = ({
    cart,
    customer,
    userId,
    clearSale,
    setReceipt
})=>{


    const [loading,setLoading] = useState(false);


    const checkout = async()=>{


        if(
            !customer ||
            cart.length === 0 ||
            !userId
        ){

            return;

        }




        const payload = {


            customerId: customer.customerId,


            userId,


            items: cart.map(item=>({


                productId:item.productId,


                quantity:item.quantity


            }))


        };





        try{


            setLoading(true);



            /*
                CREATE SALE
            */


            const sale =

                await saleService.createSale(
                    payload
                );






            /*
                FETCH RECEIPT
            */


            const receipt =

                await saleService.getReceipt(
                    sale.saleId
                );






            /*
                SHOW RECEIPT
            */


            setReceipt(receipt);






            /*
                RESET CART
            */


            clearSale();



        }

        catch(error){


            console.error(
                "Checkout failed:",
                error
            );


            alert(
                error.response?.data?.message ||
                "Failed to complete sale"
            );


        }

        finally{


            setLoading(false);


        }


    };









    return (


        <button


            className="checkout-btn"


            disabled={

                loading ||

                !customer ||

                cart.length===0 ||

                !userId

            }


            onClick={checkout}


        >



            {

                loading

                ?

                "Processing Sale..."

                :

                "Complete Sale"


            }



        </button>


    );


};



export default Checkout;