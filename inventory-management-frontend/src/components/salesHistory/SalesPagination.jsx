const SalesPagination=({

    page,

    totalPages,

    setPage

})=>{

    return(

        <div className="pagination">

            <button

                disabled={page===0}

                onClick={()=>setPage(page-1)}

            >

                Previous

            </button>

            <span>

                Page {page+1} of {totalPages}

            </span>

            <button

                disabled={page===totalPages-1}

                onClick={()=>setPage(page+1)}

            >

                Next

            </button>

        </div>

    );

};

export default SalesPagination;