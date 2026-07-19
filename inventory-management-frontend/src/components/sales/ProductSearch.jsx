import {
    useEffect,
    useState
} from "react";


import productService from "../../services/productService";

import ProductSkeleton from "./ProductSkeleton";


const ProductSearch = ({
    addToCart
}) => {


    const [products,setProducts] = useState([]);


    const [keyword,setKeyword] = useState("");


    const [loading,setLoading] = useState(false);


    const [error,setError] = useState("");








    const fetchProducts = async()=>{


        try{


            setLoading(true);

            setError("");



            let response;



            if(keyword.trim()){


                response =
                await productService.searchProducts({

                    keyword,

                    page:0,

                    size:20

                });


            }

            else{


                response =
                await productService.getActiveProducts({

                    page:0,

                    size:20

                });


            }






            setProducts(

                response.content || []

            );



        }


        catch(error){


            console.error(
                "Product loading error:",
                error
            );


            setError(
                "Failed to load products"
            );


        }


        finally{


            setLoading(false);


        }



    };









    /*
        DEBOUNCE SEARCH
    */


    useEffect(()=>{


        const timer = setTimeout(()=>{


            fetchProducts();


        },400);




        return ()=>clearTimeout(timer);



    },[keyword]);











    return (


        <div className="product-search">





            <input


                type="text"


                placeholder="Search product..."


                value={keyword}


                onChange={
                    e=>setKeyword(e.target.value)
                }


            />









            {

                error &&


                <p className="error-message">

                    {error}

                </p>


            }









            <div className="product-grid">





                {


                    loading


                    ?


                    (

                        Array.from(
                            {length:8}
                        )
                        .map((_,index)=>(


                            <ProductSkeleton

                                key={index}

                            />


                        ))

                    )



                    :



                    products.length === 0



                    ?



                    (

                        <div className="empty-products">


                            <h3>
                                No Products Found
                            </h3>


                            <p>

                                Try searching another
                                product.

                            </p>


                        </div>


                    )



                    :



                    products.map(product=>(



                        <div


                            key={
                                product.productId
                            }


                            className={

                                product.quantity <=0

                                ?

                                "product-card disabled"

                                :

                                "product-card"

                            }


                            onClick={()=>{


                                if(product.quantity >0)

                                    addToCart(product);


                            }}


                        >





                            <h4>

                                {
                                    product.productName
                                }

                            </h4>







                            <p>

                                TZS {

                                    product.sellingPrice

                                    ?.toLocaleString()

                                }

                            </p>







                            <span>

                                Stock:

                                {" "}

                                {
                                    product.quantity
                                }


                            </span>







                        </div>



                    ))



                }




            </div>





        </div>


    );


};


export default ProductSearch;