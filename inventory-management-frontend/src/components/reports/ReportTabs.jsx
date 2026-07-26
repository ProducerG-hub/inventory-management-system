import { useState } from "react";

import SalesReport from "./SalesReport";
import InventoryReport from "./InventoryReport";
import CustomerReport from "./CustomerReport";
import ProfitReport from "./ProfitReport";


const ReportTabs = ({

    sales,
    inventory,
    customers,
    profit

}) => {


    const [active,setActive] = useState("sales");



    return (

        <div className="report-tabs">


            <div className="tabs-buttons">


                <button
                    onClick={()=>setActive("sales")}
                >
                    Sales
                </button>


                <button
                    onClick={()=>setActive("inventory")}
                >
                    Inventory
                </button>


                <button
                    onClick={()=>setActive("customers")}
                >
                    Customers
                </button>


                <button
                    onClick={()=>setActive("profit")}
                >
                    Profit
                </button>


            </div>



            <div className="tab-content">


                {
                    active==="sales" &&

                    <SalesReport 
                        data={sales}
                    />

                }



                {
                    active==="inventory" &&

                    <InventoryReport
                        data={inventory}
                    />

                }



                {
                    active==="customers" &&

                    <CustomerReport
                        data={customers}
                    />

                }



                {
                    active==="profit" &&

                    <ProfitReport
                        data={profit}
                    />

                }


            </div>


        </div>

    );

};


export default ReportTabs;