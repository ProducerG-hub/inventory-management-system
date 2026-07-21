import { useEffect, useState } from "react";

import "./ProductModal.css";

import categoryService from "../../services/categoryService";
import supplierService from "../../services/supplierService";
import { useAuth } from "../../context/AuthContext";


const ProductForm = ({
    product,
    onSubmit,
    onCancel
}) => {

    const { user } = useAuth();
    
    const initialState = {

        productName: "",

        buyingPrice: "",

        sellingPrice: "",

        quantity: "",

        isActive: true,

        categoryId: "",

        supplierId: "",

    };



    const [form, setForm] = useState(initialState);


    const [categories, setCategories] = useState([]);


    const [suppliers, setSuppliers] = useState([]);


    const [loadingData, setLoadingData] = useState(true);






    /*
        Load Categories and Suppliers
    */

    useEffect(() => {


        const loadFormData = async () => {


            try {


                const [
                    categoriesData,
                    suppliersData
                ] = await Promise.all([


                    categoryService.getCategories(),


                    supplierService.getSuppliers()


                ]);



                setCategories(categoriesData);


                setSuppliers(suppliersData);



            } catch(error){


                console.error(
                    "Failed loading form data:",
                    error
                );


            } finally {


                setLoadingData(false);


            }


        };



        loadFormData();


    }, []);







    /*
        Populate Form For Edit
    */


    useEffect(() => {


        if(product){


            setForm({


                productName: product.productName,


                buyingPrice: product.buyingPrice,


                sellingPrice: product.sellingPrice,


                quantity: product.quantity,


                isActive: product.isActive,


                categoryId: product.categoryId,


                supplierId: product.supplierId


            });



        }else{


            setForm(initialState);


        }



    }, [product]);








    const handleChange = (e) => {


        const {
            name,
            value
        } = e.target;



        setForm({

            ...form,

            [name]:value

        });



    };








    const handleSubmit = (e) => {


        e.preventDefault();




        const requestData = {


            productName: form.productName,


            buyingPrice:Number(form.buyingPrice),


            sellingPrice:Number(form.sellingPrice),


            quantity:Number(form.quantity),


            isActive:form.isActive,


            categoryId:Number(form.categoryId),


            supplierId:Number(form.supplierId),

            userId: user.userId || user.id



        };





        console.log(
            "Product V3 Payload:",
            requestData
        );



        onSubmit(requestData);



    };








    return (


        <form

            className="product-form"

            onSubmit={handleSubmit}

        >




            <div className="product-form-grid">







                <input

                    name="productName"

                    placeholder="Product Name"

                    value={form.productName}

                    onChange={handleChange}

                    required

                />







                <input

                    type="number"

                    name="buyingPrice"

                    placeholder="Buying Price"

                    value={form.buyingPrice}

                    onChange={handleChange}

                    required

                />








                <input

                    type="number"

                    name="sellingPrice"

                    placeholder="Selling Price"

                    value={form.sellingPrice}

                    onChange={handleChange}

                    required

                />








                <input

                    type="number"

                    name="quantity"

                    placeholder="Quantity"

                    value={form.quantity}

                    onChange={handleChange}

                    required

                />









                <select


                    name="isActive"


                    value={form.isActive}


                    onChange={(e)=>

                        setForm({

                            ...form,

                            isActive:

                            e.target.value==="true"

                        })

                    }


                >



                    <option value="true">

                        Active

                    </option>




                    <option value="false">

                        Inactive

                    </option>



                </select>









                <select


                    name="categoryId"


                    value={form.categoryId}


                    onChange={handleChange}


                    required


                >



                    <option value="" disabled>


                        {
                            loadingData

                            ?

                            "Loading..."

                            :

                            "Select Category"

                        }


                    </option>





                    {
                        categories.map((category)=>(


                            <option

                                key={category.categoryId}

                                value={category.categoryId}

                            >


                                {category.categoryName}



                            </option>



                        ))
                    }




                </select>









                <select


                    name="supplierId"


                    value={form.supplierId}


                    onChange={handleChange}


                    required


                >



                    <option value="" disabled>


                        {
                            loadingData

                            ?

                            "Loading..."

                            :

                            "Select Supplier"

                        }


                    </option>





                    {
                        suppliers.map((supplier)=>(


                            <option

                                key={supplier.supplierId}

                                value={supplier.supplierId}

                            >


                                {supplier.supplierName}



                            </option>



                        ))
                    }




                </select>






            </div>








            <div className="form-actions">





                <button

                    type="submit"

                    className="save-btn"

                >

                    Save

                </button>






                <button

                    type="button"

                    className="cancel-btn"

                    onClick={onCancel}

                >

                    Cancel

                </button>





            </div>





        </form>


    );


};


export default ProductForm;