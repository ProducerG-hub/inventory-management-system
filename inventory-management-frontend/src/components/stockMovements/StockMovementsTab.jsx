import { useEffect, useState } from "react";

import stockMovementService from "../../services/stockMovementService";


const StockMovementsTab = ()=>{


    const [movements,setMovements]=useState([]);

    const [loading,setLoading]=useState(false);




    useEffect(()=>{


        loadMovements();


    },[]);





    const loadMovements = async()=>{


        try{


            setLoading(true);


            const data =
                await stockMovementService.getMovements({

                    page:0,

                    size:10,

                    sortBy:"movementId",

                    sortDir:"desc"

                });


            setMovements(data.content);


        }
        finally{


            setLoading(false);


        }


    };





    if(loading){

        return <p>Loading stock movements...</p>;

    }





    return(


        <div className="stock-movements-tab">


            {

                movements.length === 0 ?

                (

                    <p>
                        No stock movements found.
                    </p>

                )

                :

                (

                    <table>


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


                            </tr>


                        </thead>




                        <tbody>


                            {
                                movements.map(
                                    movement=>(


                                    <tr key={movement.movementId}>


                                        <td>
                                            {
                                                movement.productName
                                            }
                                        </td>



                                        <td>
                                            {
                                                movement.movementType
                                            }
                                        </td>



                                        <td>
                                            {
                                                movement.quantity
                                            }
                                        </td>



                                        <td>
                                            {
                                                movement.userFullName
                                            }
                                        </td>



                                        <td>

                                            {
                                                new Date(
                                                    movement.movementDate
                                                )
                                                .toLocaleString()
                                            }

                                        </td>



                                    </tr>


                                ))

                            }


                        </tbody>


                    </table>


                )

            }


        </div>


    );

};


export default StockMovementsTab;