import { useEffect, useState } from "react";

import { useAuth } from "../../context/AuthContext";

import supplierService from "../../services/supplierService";

import SupplierSearch from "../../components/suppliers/SupplierSearch";
import SupplierTable from "../../components/suppliers/SupplierTable";
import SupplierPagination from "../../components/suppliers/SupplierPagination";
import SupplierModal from "../../components/suppliers/SupplierModal";

import ConfirmModal from "../../components/common/ConfirmModal";

import toast from "react-hot-toast";

import "./Suppliers.css";


const Suppliers = () => {


    const { user } = useAuth();



    const [suppliers,setSuppliers] = useState([]);

    const [keyword,setKeyword] = useState("");

    const [page,setPage] = useState(0);

    const [totalPages,setTotalPages] = useState(0);

    const [activeTab,setActiveTab] = useState("active");



    const [showModal,setShowModal] = useState(false);

    const [selectedSupplier,setSelectedSupplier] = useState(null);



    const [showDeleteModal,setShowDeleteModal] = useState(false);

    const [supplierToDelete,setSupplierToDelete] = useState(null);






    const loadSuppliers = async()=>{


        try{


            let response;


            const params = {

                page,

                size:10,

                sortBy:"supplierId",

                sortDir:"asc"

            };



            const active =
                activeTab === "active";




            if(keyword.trim()){


                response =
                await supplierService.searchSuppliers({

                    ...params,

                    keyword,

                    active

                });



            }else{


                if(active){

                    response =
                    await supplierService.getActiveSuppliers(params);


                }else{


                    response =
                    await supplierService.getInactiveSuppliers(params);


                }


            }



            setSuppliers(response.content);

            setTotalPages(response.totalPages);



        }catch(error){


            console.error(error);


            toast.error(
                "Failed to load suppliers"
            );


        }


    };







    useEffect(()=>{


        loadSuppliers();


    },[page,keyword,activeTab]);







    const handleRestore = async(supplier)=>{


        const loading =
        toast.loading(
            "Restoring supplier..."
        );



        try{


            await supplierService.restoreSupplier(
                supplier.supplierId
            );



            toast.dismiss(loading);


            toast.success(
                "Supplier restored successfully"
            );


            loadSuppliers();



        }catch(error){


            toast.dismiss(loading);


            toast.error(
                "Restore failed"
            );


        }


    };









    const handleSave = async(data)=>{


        const loading =
        toast.loading(

            selectedSupplier

            ?

            "Updating supplier..."

            :

            "Creating supplier..."

        );



        try{


            if(selectedSupplier){


                await supplierService.updateSupplier(

                    selectedSupplier.supplierId,

                    data

                );


                toast.dismiss(loading);


                toast.success(
                    "Supplier updated successfully"
                );


            }else{


                await supplierService.createSupplier(
                    data
                );


                toast.dismiss(loading);


                toast.success(
                    "Supplier created successfully"
                );


            }



            setShowModal(false);


            setSelectedSupplier(null);


            loadSuppliers();



        }catch(error){


            toast.dismiss(loading);


            console.error(error);


            toast.error(
                "Operation failed"
            );


        }


    };









    const handleDelete = async()=>{


        if(!supplierToDelete)
            return;




        const loading =
        toast.loading(
            "Deactivating supplier..."
        );



        try{


            await supplierService.deleteSupplier(

                supplierToDelete.supplierId

            );



            toast.dismiss(loading);



            toast.success(
                "Supplier deactivated successfully"
            );



            setShowDeleteModal(false);


            setSupplierToDelete(null);



            loadSuppliers();



        }catch(error){


            toast.dismiss(loading);


            toast.error(
                "Failed to deactivate supplier"
            );


        }


    };










return (

<div className="suppliers-page">



<div className="suppliers-header">


<div>

<h2>
Suppliers
</h2>


<p>
Manage inventory suppliers
</p>


</div>




{
user?.role==="ADMIN" && (


<button

className="primary-btn"

onClick={()=>{

setSelectedSupplier(null);

setShowModal(true);

}}

>

+ Add Supplier

</button>


)
}



</div>







{
user?.role==="ADMIN" && (


<div className="suppliers-tabs">


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

Active Suppliers

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

Inactive Suppliers

</button>


</div>


)
}






<SupplierSearch

keyword={keyword}

setKeyword={(value)=>{

setPage(0);

setKeyword(value);

}}

/>







<SupplierTable

suppliers={suppliers}

activeTab={activeTab}

onEdit={(supplier)=>{

setSelectedSupplier(supplier);

setShowModal(true);

}}

onDelete={(supplier)=>{

setSupplierToDelete(supplier);

setShowDeleteModal(true);

}}

onRestore={handleRestore}

/>






<SupplierPagination

currentPage={page}

totalPages={totalPages}

setPage={setPage}

/>







{
showModal && (

<SupplierModal

supplier={selectedSupplier}

onClose={()=>{

setShowModal(false);

}}

onSubmit={handleSave}

/>

)
}







<ConfirmModal

isOpen={showDeleteModal}

title="Deactivate Supplier"

message={`Are you sure you want to deactivate "${supplierToDelete?.supplierName}"?`}

confirmText="Deactivate"

cancelText="Cancel"

onConfirm={handleDelete}

onCancel={()=>{

setShowDeleteModal(false);

setSupplierToDelete(null);

}}

/>





</div>

);


};


export default Suppliers;