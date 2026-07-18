const UserPagination = ({

    currentPage,

    totalPages,

    setPage

}) => {


    return (

        <div className="pagination">


            <button

                disabled={currentPage === 0}

                onClick={() => setPage(currentPage - 1)}

            >

                Previous

            </button>



            <span>

                Page {currentPage + 1} of {totalPages}

            </span>




            <button

                disabled={currentPage + 1 === totalPages}

                onClick={() => setPage(currentPage + 1)}

            >

                Next

            </button>



        </div>

    );


};


export default UserPagination;