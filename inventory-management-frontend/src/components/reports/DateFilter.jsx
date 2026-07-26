import { useState } from "react";


const DateFilter = ({ onFilter }) => {


    const [startDate, setStartDate] = useState("");

    const [endDate, setEndDate] = useState("");



    const handleSubmit = (e) => {

        e.preventDefault();


        onFilter({

            startDate,

            endDate

        });


    };



    const clearFilter = () => {


        setStartDate("");

        setEndDate("");


        onFilter({

            startDate: "",

            endDate: ""

        });


    };



    return (

        <div className="date-filter">


            <form onSubmit={handleSubmit}>


                <div className="filter-group">


                    <label>
                        From
                    </label>


                    <input

                        type="date"

                        value={startDate}

                        onChange={(e)=>
                            setStartDate(
                                e.target.value
                            )
                        }

                    />


                </div>




                <div className="filter-group">


                    <label>
                        To
                    </label>


                    <input

                        type="date"

                        value={endDate}

                        onChange={(e)=>
                            setEndDate(
                                e.target.value
                            )
                        }

                    />


                </div>




                <button type="submit">

                    Apply Filter

                </button>



                <button

                    type="button"

                    onClick={clearFilter}

                >

                    Clear

                </button>



            </form>


        </div>

    );

};


export default DateFilter;