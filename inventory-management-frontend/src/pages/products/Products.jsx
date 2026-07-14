import { useEffect, useState } from "react";


import { useAuth } from "../../context/AuthContext";


import productService from "../../services/productService";


import ProductSearch from "../../components/products/ProductSearch";

import ProductTable from "../../components/products/ProductTable";

import ProductPagination from "../../components/products/ProductPagination";

import ProductModal from "../../components/products/ProductModal";
import toast from "react-hot-toast";
import "./Products.css";
import ConfirmModal from "../../components/common/ConfirmModal";




const Products = () => {



    const { user } = useAuth();




    const [products,setProducts] = useState([]);
    const [keyword,setKeyword] = useState("");
    const [page,setPage] = useState(0);
    const [activeTab, setActiveTab] = useState("active");
    const [totalPages,setTotalPages] = useState(0);

    const [showModal,setShowModal] = useState(false);
    const [selectedProduct,setSelectedProduct] = useState(null);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [productToDelete, setProductToDelete] = useState(null);

    





    const loadProducts = async()=>{


        try{


            let response;
            const params={
                page,
                size:10,
                sortBy:"productId",
                sortDir:"asc"
            };





            if(keyword.trim()){


                response = await productService.searchProducts({

                    ...params,

                    keyword

                });


            }else{


                if(keyword.trim()){

                    response = await productService.searchProducts({...params,keyword,active: activeTab });

            }else{

                if(activeTab==="active"){

                    response = await productService.getActiveProducts(params);

                }else{

                    response = await productService.getInactiveProducts(params);

                }

            }


            }

            setProducts(response.content);
            setTotalPages(response.totalPages);
        }catch(error){

            toast.error(
                "Failed to load products"
            );
        }
    };
    useEffect(()=>{


        loadProducts();


    },[page,keyword,activeTab]);

    const handleRestore = async(product)=>{

    const loading = toast.loading(
        "Restoring product..."
    );

    try{

        await productService.restoreProduct(
            product.productId
        );

        toast.dismiss(loading);

        toast.success(
            "Product restored successfully"
        );

        loadProducts();

    }catch(error){

        toast.dismiss(loading);

        toast.error(
            "Restore failed"
        );

    }

};



    const handleSave = async(data)=>{
        const savingToast = toast.loading(
            selectedProduct ? "Updating product..." : "Creating product..."
        );

        try{



            if(selectedProduct){



                await productService.updateProduct(

                    selectedProduct.productId,

                    data

                );
                toast.dismiss(savingToast);

                toast.success(
                    "Product updated successfully"
                );




            }else{
                await productService.createProduct(data);

                toast.dismiss(savingToast);
                toast.success(
                    "Product created successfully"
                );
            }





            setShowModal(false);



            setSelectedProduct(null);



            loadProducts();





        }catch(error){


            console.error(
                "Save Product Error:",
                error.response?.data || error
            );



            toast.error(
                "Operation failed"
            );



        }



    };

    const handleDelete = async () => {

    if (!productToDelete) return;

    const deletingToast = toast.loading(
        "Deactivating product..."
    );

    try {

        await productService.deleteProduct(
            productToDelete.productId
        );

        toast.dismiss(deletingToast);

        toast.success(
            "Product deactivated successfully"
        );

        setShowDeleteModal(false);

        setProductToDelete(null);

        loadProducts();

    } catch (error) {

        console.error(error);

        toast.dismiss(deletingToast);

        toast.error(
            "Failed to deactivate product"
        );

    }

};




    return (


        <div className="products-page">





            <div className="products-header">



                <div>


                    <h2>
                        Products
                    </h2>


                    <p>
                        Manage inventory products
                    </p>


                </div>





                {
                    user?.role === "ADMIN" && (


                        <button

                            className="primary-btn"

                            onClick={()=>{


                                setSelectedProduct(null);


                                setShowModal(true);



                            }}

                        >

                            + Add Product

                        </button>


                    )
                }





            </div>

                {
                user?.role==="ADMIN" && (

                    <div className="products-tabs">

                        <button

                            className={
                                activeTab==="active"
                                ? "tab-btn active"
                                : "tab-btn"
                            }

                            onClick={()=>{

                                setPage(0);

                                setActiveTab("active");

                            }}

                        >

                            Active Products

                        </button>

                        <button

                            className={
                                activeTab==="inactive"
                                ? "tab-btn active"
                                : "tab-btn"
                            }

                            onClick={()=>{

                                setPage(0);

                                setActiveTab("inactive");

                            }}

                        >

                            Inactive Products

                        </button>

                    </div>

                )
            }
            <ProductSearch

                keyword={keyword}

                setKeyword={(value)=>{


                    setPage(0);


                    setKeyword(value);



                }}

            />







            <ProductTable

                products={products}
                 activeTab={activeTab}
                onRestore={handleRestore}
                onEdit={(product)=>{
                    setSelectedProduct(product);


                    setShowModal(true);



                }}



                onDelete={(product) => {
                    setProductToDelete(product);
                    setShowDeleteModal(true);
                }}


            />

            <ProductPagination

                currentPage={page}

                totalPages={totalPages}

                setPage={setPage}

            />

            {
                showModal && (


                    <ProductModal


                        product={selectedProduct}


                        onClose={()=>{


                            setShowModal(false);

                        }}



                        onSubmit={handleSave}



                    />


                )
            }

            <ConfirmModal

    isOpen={showDeleteModal}

    title="Deactivate Product"

    message={`Are you sure you want to deactivate "${productToDelete?.productName}"?`}

    confirmText="Deactivate"

    cancelText="Cancel"

    onConfirm={handleDelete}

    onCancel={() => {

        setShowDeleteModal(false);

        setProductToDelete(null);

    }}

/>







        </div>


    );



};



export default Products;