const SupplierSearch = ({
    keyword,
    setKeyword
}) => {


    return (

        <div className="supplier-search">


            <input

                type="text"

                placeholder="Search supplier..."

                value={keyword}

                onChange={(e)=>setKeyword(e.target.value)}

            />


        </div>

    );


};


export default SupplierSearch;