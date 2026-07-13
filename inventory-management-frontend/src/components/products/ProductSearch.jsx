const ProductSearch = ({keyword,setKeyword}) => {


    return (

        <div className="product-search">

            <input

                type="text"

                placeholder="Search product..."

                value={keyword}

                onChange={(e)=>setKeyword(e.target.value)}

            />

        </div>

    );

};


export default ProductSearch;