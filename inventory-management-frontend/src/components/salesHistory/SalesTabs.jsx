const SalesTabs=({

    activeTab,

    setActiveTab

})=>{

    return(

        <div className="sales-tabs">

            <button

                className={

                    activeTab==="sales"

                    ?

                    "tab-btn active"

                    :

                    "tab-btn"

                }

                onClick={()=>setActiveTab("sales")}

            >

                Sales History

            </button>

            <button

                className={

                    activeTab==="movements"

                    ?

                    "tab-btn active"

                    :

                    "tab-btn"

                }

                onClick={()=>setActiveTab("movements")}

            >

                Stock Movements

            </button>

        </div>

    );

};

export default SalesTabs;