import { useState } from "react";

import SalesReport from "./SalesReport";
import InventoryReport from "./InventoryReport";
import CustomerReport from "./CustomerReport";
import ProfitReport from "./ProfitReport";


const ReportTabs = ({
    tabs = [],
    activeTab,
    onTabChange,

    sales,
    inventory,
    customers,
    profit

}) => {


    return (

        <div className="report-tabs mb-4">


            <div className="tabs-buttons">


                {tabs.map((tab) => (
                    <button
                        key={tab.key}
                        type="button"
                        className={activeTab === tab.key ? "tab-button is-active" : "tab-button"}
                        onClick={() => onTabChange(tab.key)}
                        aria-pressed={activeTab === tab.key}
                    >
                        {tab.label}
                    </button>
                ))}


            </div>



            <div className="tab-content">


                {
                    activeTab==="sales" &&

                    <SalesReport 
                        data={sales}
                    />

                }



                {
                    activeTab==="inventory" &&

                    <InventoryReport
                        data={inventory}
                    />

                }



                {
                    activeTab==="customers" &&

                    <CustomerReport
                        data={customers}
                    />

                }



                {
                    activeTab==="profit" &&

                    <ProfitReport
                        data={profit}
                    />

                }


            </div>


        </div>

    );

};


export default ReportTabs;