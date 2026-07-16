import SupplierForm from "./SupplierForm";

import "./SupplierModal.css";


const SupplierModal = ({
    supplier,
    onClose,
    onSubmit
}) => {


    return (

        <div className="modal-overlay">


            <div className="supplier-modal">


                <h3 className="modal-header">


                    {
                        supplier

                        ?

                        "Edit Supplier"

                        :

                        "Add Supplier"
                    }


                </h3>





                <SupplierForm


                    supplier={supplier}


                    onSubmit={onSubmit}


                    onCancel={onClose}



                />



            </div>



        </div>


    );


};


export default SupplierModal;