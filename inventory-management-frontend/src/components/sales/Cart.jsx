import CartItem from "./CartItem";


const Cart = ({
    cart,
    updateQuantity,
    removeFromCart
})=>{


    const total = cart.reduce(

        (sum,item)=>

            sum +
            (
                item.sellingPrice *
                item.quantity
            ),

        0

    );




    return (

        <div className="cart">


            <h2>
                Shopping Cart
            </h2>



            {
                cart.length === 0

                ?

                (

                    <div className="empty-cart">


                        <div className="empty-icon">
                            🛒
                        </div>


                        <h3>
                            Cart is Empty
                        </h3>


                        <p>
                            Select products from the list
                            to start a new sale.
                        </p>


                    </div>


                )


                :


                (

                    <>


                        <div className="cart-items">


                            {
                                cart.map(item=>(


                                    <CartItem


                                        key={
                                            item.productId
                                        }


                                        item={item}


                                        updateQuantity={
                                            updateQuantity
                                        }


                                        removeFromCart={
                                            removeFromCart
                                        }


                                    />


                                ))

                            }


                        </div>





                        <div className="cart-total">


                            <span>
                                Total
                            </span>


                            <strong>

                                TZS {total.toLocaleString()}

                            </strong>


                        </div>



                    </>

                )


            }



        </div>

    );


};


export default Cart;