import CategoryForm from "./CategoryForm";

import "./CategoryModal.css";


const CategoryModal = ({
    category,
    onClose,
    onSubmit
}) => {


    return (

        <div className="modal-overlay">


            <div className="category-modal">


                <h3 className="modal-header">

                    {
                        category
                        ?
                        "Edit Category"
                        :
                        "Add Category"
                    }

                </h3>





                <CategoryForm

                    category={category}

                    onSubmit={onSubmit}

                    onCancel={onClose}

                />



            </div>



        </div>

    );


};


export default CategoryModal;