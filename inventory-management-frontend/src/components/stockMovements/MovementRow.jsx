import React from "react";


const MovementRow = ({
    movement
}) => {



    const formatDate = (date)=>{


        return new Date(date)
            .toLocaleString(
                "en-TZ",
                {
                    dateStyle:"medium",
                    timeStyle:"short"
                }
            );

    };




    return (

        <tr>


            <td>


                <div className="product-cell">


                    <strong>

                        {
                            movement.productName
                        }

                    </strong>


                    <span>

                        ID: {movement.productId}

                    </span>


                </div>


            </td>





            <td>


                <span

                    className={
                        movement.movementType === "IN"

                        ?

                        "movement-badge movement-in"

                        :

                        "movement-badge movement-out"
                    }

                >


                    {
                        movement.movementType
                    }


                </span>


            </td>





            <td>


                <span

                    className={
                        movement.movementType === "IN"

                        ?

                        "quantity-positive"

                        :

                        "quantity-negative"
                    }

                >


                    {
                        movement.movementType === "IN"
                        ?
                        "+"
                        :
                        "-"
                    }


                    {
                        movement.quantity
                    }


                </span>


            </td>





            <td>


                <div className="user-cell">


                    <span className="user-icon">

                        👤

                    </span>


                    {
                        movement.userFullName
                    }


                </div>


            </td>





            <td>


                {
                    formatDate(
                        movement.movementDate
                    )
                }


            </td>





            <td>


                {
                    movement.remarks ||
                    "-"
                }


            </td>


        </tr>

    );

};


export default MovementRow;