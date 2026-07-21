import React from "react";
import MovementRow from "./MovementRow";


const MovementTable = ({
    movements
}) => {


    return (

        <div className="movement-table-wrapper">


            <table className="movement-table">


                <thead>

                    <tr>

                        <th>
                            Product
                        </th>


                        <th>
                            Type
                        </th>


                        <th>
                            Quantity
                        </th>


                        <th>
                            User
                        </th>


                        <th>
                            Date
                        </th>


                        <th>
                            Remarks
                        </th>


                    </tr>


                </thead>




                <tbody>


                    {
                        movements.map(
                            movement => (

                                <MovementRow

                                    key={
                                        movement.movementId
                                    }

                                    movement={movement}

                                />

                            )
                        )
                    }


                </tbody>


            </table>


        </div>

    );

};


export default MovementTable;