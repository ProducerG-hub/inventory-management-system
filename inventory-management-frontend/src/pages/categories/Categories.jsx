import { useEffect, useState } from "react";


import { useAuth } from "../../context/AuthContext";


import categoryService from "../../services/categoryService";


import CategorySearch from "../../components/categories/CategorySearch";

import CategoryTable from "../../components/categories/CategoryTable";

import CategoryPagination from "../../components/categories/CategoryPagination";

import CategoryModal from "../../components/categories/CategoryModal";


import ConfirmModal from "../../components/common/ConfirmModal";


import toast from "react-hot-toast";


import "./Categories.css";




const Category = () => {



    const { user } = useAuth();




    const [categories,setCategories] = useState([]);

    const [keyword,setKeyword] = useState("");

    const [page,setPage] = useState(0);

    const [totalPages,setTotalPages] = useState(0);

    const [activeTab,setActiveTab] = useState("active");



    const [showModal,setShowModal] = useState(false);


    const [selectedCategory,setSelectedCategory] = useState(null);



    const [showDeleteModal,setShowDeleteModal] = useState(false);


    const [categoryToDelete,setCategoryToDelete] = useState(null);







    const loadCategories = async()=>{


        try{


            const params = {

                page,

                size:10,

                sortBy:"categoryId",

                sortDir:"asc",

                active: activeTab === "active"

            };




            let response;




            if(keyword.trim()){


                response = await categoryService.searchCategories({

                    ...params,

                    keyword

                });



            }else{


                response = await categoryService.getAllCategories(
                    params
                );


            }




            setCategories(
                response.content
            );


            setTotalPages(
                response.totalPages
            );



        }catch(error){


            console.error(error);


            toast.error(
                "Failed to load categories"
            );


        }


    };







    useEffect(()=>{


        loadCategories();


    },[page,keyword,activeTab]);








    const handleRestore = async(category)=>{


        const loading = toast.loading(
            "Restoring category..."
        );



        try{


            await categoryService.restoreCategory(

                category.categoryId

            );



            toast.dismiss(loading);


            toast.success(
                "Category restored successfully"
            );



            loadCategories();



        }catch(error){


            toast.dismiss(loading);


            toast.error(
                "Restore failed"
            );


        }


    };









    const handleSave = async(data)=>{


        const loading = toast.loading(

            selectedCategory

            ?

            "Updating category..."

            :

            "Creating category..."

        );



        try{


            if(selectedCategory){



                await categoryService.updateCategory(

                    selectedCategory.categoryId,

                    data

                );



                toast.dismiss(loading);



                toast.success(
                    "Category updated successfully"
                );



            }else{


                await categoryService.createCategory(
                    data
                );


                toast.dismiss(loading);


                toast.success(
                    "Category created successfully"
                );



            }






            setShowModal(false);


            setSelectedCategory(null);



            loadCategories();





        }catch(error){



            console.error(
                "Category save error:",
                error.response?.data || error
            );



            toast.dismiss(loading);


            toast.error(
                "Operation failed"
            );



        }


    };









    const handleDelete = async()=>{


        if(!categoryToDelete)
            return;



        const loading = toast.loading(
            "Deactivating category..."
        );



        try{


            await categoryService.deleteCategory(

                categoryToDelete.categoryId

            );



            toast.dismiss(loading);



            toast.success(
                "Category deactivated successfully"
            );



            setShowDeleteModal(false);


            setCategoryToDelete(null);



            loadCategories();



        }catch(error){



            toast.dismiss(loading);



            toast.error(
                "Failed to deactivate category"
            );



        }


    };








    return (

        <div className="categories-page">





            <div className="categories-header">



                <div>


                    <h2>
                        Categories
                    </h2>


                    <p>
                        Manage product categories
                    </p>


                </div>






                {
                    user?.role === "ADMIN" && (


                        <button

                            className="primary-btn"

                            onClick={()=>{


                                setSelectedCategory(null);


                                setShowModal(true);



                            }}

                        >

                            + Add Category


                        </button>


                    )
                }



            </div>


            {
                user?.role === "ADMIN" && (


                    <div className="categories-tabs">


                        <button

                            className={
                                activeTab==="active"

                                ?

                                "tab-btn active"

                                :

                                "tab-btn"
                            }


                            onClick={()=>{


                                setPage(0);


                                setActiveTab("active");


                            }}

                        >

                            Active Categories

                        </button>





                        <button

                            className={
                                activeTab==="inactive"

                                ?

                                "tab-btn active"

                                :

                                "tab-btn"
                            }



                            onClick={()=>{


                                setPage(0);


                                setActiveTab("inactive");


                            }}

                        >

                            Inactive Categories

                        </button>



                    </div>


                )
            }

            <CategorySearch

                keyword={keyword}


                setKeyword={(value)=>{


                    setPage(0);


                    setKeyword(value);



                }}

            />

            <CategoryTable

                categories={categories}

                activeTab={activeTab}


                onRestore={handleRestore}


                onEdit={(category)=>{


                    setSelectedCategory(category);


                    setShowModal(true);


                }}


                onDelete={(category)=>{


                    setCategoryToDelete(category);


                    setShowDeleteModal(true);



                }}


            />

            <CategoryPagination

                currentPage={page}

                totalPages={totalPages}

                setPage={setPage}


            />

            {

                showModal && (


                    <CategoryModal


                        category={selectedCategory}



                        onClose={()=>{


                            setShowModal(false);



                        }}



                        onSubmit={handleSave}



                    />


                )


            }


            <ConfirmModal


                isOpen={showDeleteModal}


                title="Deactivate Category"


                message={
                    `Are you sure you want to deactivate "${categoryToDelete?.categoryName}"?`
                }


                confirmText="Deactivate"


                cancelText="Cancel"



                onConfirm={handleDelete}



                onCancel={()=>{


                    setShowDeleteModal(false);


                    setCategoryToDelete(null);



                }}



            />

        </div>


    );


};



export default Category;