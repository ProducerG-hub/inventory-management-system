import React from "react";


const MovementSummaryCards = ({
    totalMovements = 0,
    totalIn = 0,
    totalOut = 0,
    todayMovements = 0
}) => {


    const cards = [

        {
            title:"Total Movements",
            value:totalMovements,
            icon:"📦"
        },


        {
            title:"Stock IN",
            value:totalIn,
            icon:"⬆️"
        },


        {
            title:"Stock OUT",
            value:totalOut,
            icon:"⬇️"
        },


        {
            title:"Today",
            value:todayMovements,
            icon:"📅"
        }

    ];





    return (

        <div className="movement-summary">


            {
                cards.map((card,index)=>(


                    <div
                        className="movement-card"
                        key={index}
                    >


                        <div className="movement-icon">

                            {card.icon}

                        </div>



                        <div>


                            <p>
                                {card.title}
                            </p>



                            <h3>
                                {card.value}
                            </h3>


                        </div>



                    </div>


                ))
            }


        </div>

    );

};


export default MovementSummaryCards;