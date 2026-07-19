import { useState } from "react";


import ProductSearch from "../../components/sales/ProductSearch";
import CustomerSelect from "../../components/sales/CustomerSelect";
import Cart from "../../components/sales/Cart";
import Checkout from "../../components/sales/Checkout";
import ReceiptPreview from "../../components/sales/ReceiptPreview";


import { useAuth } from "../../context/AuthContext";

import "./SalesPage.css";



const SalesPage = () => {



    const { user } = useAuth();



    const [cart,setCart] = useState([]);


    const [customer,setCustomer] = useState(null);


    const [receipt,setReceipt] = useState(null);







    /*
        ADD PRODUCT TO CART
    */


    const addToCart = (product)=>{


        const existing = cart.find(

            item =>
            item.productId === product.productId

        );




        if(existing){


            setCart(

                cart.map(item =>

                    item.productId === product.productId

                    ?

                    {
                        ...item,

                        quantity:item.quantity + 1
                    }


                    :

                    item

                )

            );


            return;

        }






        setCart([

            ...cart,


            {

                productId:product.productId,

                productName:product.productName,

                sellingPrice:product.sellingPrice,

                quantity:1

            }


        ]);



    };









    /*
        UPDATE CART QUANTITY
    */


    const updateQuantity = (
        productId,
        quantity
    )=>{


        if(quantity < 1)
            return;




        setCart(

            cart.map(item =>


                item.productId === productId

                ?

                {

                    ...item,

                    quantity

                }


                :

                item


            )

        );


    };









    /*
        REMOVE ITEM
    */


    const removeFromCart = (productId)=>{


        setCart(

            cart.filter(item =>

                item.productId !== productId

            )

        );


    };









    /*
        RESET SALE
    */


    const clearSale = ()=>{


        setCart([]);


        setCustomer(null);


    };











    return (


        <div className="sales-page">





            {/* PRODUCTS */}


            <section className="sales-products">


                <ProductSearch

                    addToCart={addToCart}

                />


            </section>









            {/* RIGHT PANEL */}


            <section className="sales-panel">





                <CustomerSelect


                    customer={customer}


                    setCustomer={setCustomer}


                />








                <Cart


                    cart={cart}


                    updateQuantity={updateQuantity}


                    removeFromCart={removeFromCart}


                />









                <Checkout


                    cart={cart}


                    customer={customer}


                    userId={
                        user?.userId || user?.id
                    }


                    clearSale={clearSale}


                    setReceipt={setReceipt}


                />




            </section>









            {/* RECEIPT PREVIEW */}



            {

                receipt && (


                    <ReceiptPreview


                        receipt={receipt}


                        onClose={

                            ()=>setReceipt(null)

                        }


                    />


                )

            }




        </div>



    );

};



export default SalesPage;