import ProductForm from "./ProductForm";

import "./ProductModal.css";


const ProductModal = ({
    product,
    onClose,
    onSubmit
}) => {


    return (

        <div className="modal-overlay">


            <div className="product-modal">


                <h3 className="modal-header">

                    {
                        product
                        ?
                        "Edit Product"
                        :
                        "Add Product"
                    }

                </h3>



                <ProductForm

                    product={product}

                    onSubmit={onSubmit}

                    onCancel={onClose}

                />


            </div>


        </div>

    );


};


export default ProductModal;