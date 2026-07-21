import React from "react";


const MovementSearch = ({
    keyword,
    setKeyword,
    movementType,
    setMovementType
}) => {


    return (

        <div className="movement-search-container">


            <div className="movement-search">


                <input

                    value={keyword}

                    onChange={(e)=>setKeyword(e.target.value)}

                    placeholder="
                    Search product, movement type...
                    "

                />


            </div>





            <select

                value={movementType}

                onChange={(e)=>
                    setMovementType(e.target.value)
                }

            >

                <option value="">
                    All Movements
                </option>


                <option value="IN">
                    Stock IN
                </option>


                <option value="OUT">
                    Stock OUT
                </option>


            </select>


        </div>

    );

};


export default MovementSearch;