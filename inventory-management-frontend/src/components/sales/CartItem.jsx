const CartItem = ({
    item,
    updateQuantity,
    removeFromCart
})=>{


    return (

        <div className="cart-item">


            <div>


                <h4>
                    {item.productName}
                </h4>


                <span>
                    Tsh {Number(item.sellingPrice).toLocaleString()}
                </span>


            </div>





            <div className="quantity-control">


                <button

                    onClick={()=>
                        updateQuantity(
                            item.productId,
                            item.quantity-1
                        )
                    }

                >
                    -
                </button>




                <span>
                    {item.quantity}
                </span>




                <button

                    onClick={()=>
                        updateQuantity(
                            item.productId,
                            item.quantity+1
                        )
                    }

                >
                    +
                </button>


            </div>






            <div>


                <p>

                    Tsh {
                        (
                        item.quantity *
                        item.sellingPrice
                        )
                        .toLocaleString()

                    }

                </p>



                <button

                    onClick={()=>
                        removeFromCart(item.productId)
                    }

                >

                    Remove

                </button>


            </div>



        </div>

    );


};


export default CartItem;