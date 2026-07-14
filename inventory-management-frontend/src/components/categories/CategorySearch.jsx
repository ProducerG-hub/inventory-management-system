const CategorySearch = ({keyword, setKeyword}) => {


    return (

        <div className="category-search">


            <input

                type="text"

                placeholder="Search category..."

                value={keyword}

                onChange={(e)=>setKeyword(e.target.value)}

            />


        </div>

    );


};


export default CategorySearch;