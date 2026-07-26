import { useEffect, useState } from "react";

import reportService from "../../services/reportService";

import ReportHeader from "../../components/reports/ReportHeader";
import ReportCards from "../../components/reports/ReportCards";
import ReportTabs from "../../components/reports/ReportTabs";
import Charts from "../../components/reports/Charts";
import DateFilter from "../../components/reports/DateFilter";
import ExportButtons from "../../components/reports/ExportButtons";

import "./Reports.css";


const ReportsPage = () => {


    const [summary, setSummary] = useState(null);

    const [sales, setSales] = useState([]);

    const [inventory, setInventory] = useState([]);

    const [customers, setCustomers] = useState([]);

    const [profit, setProfit] = useState([]);

    const [salesTrend, setSalesTrend] = useState([]);

    const [topSelling, setTopSelling] = useState([]);

    const [filters,setFilters] = useState({
    startDate:"",
    endDate:""
    });

    const [activeReport,setActiveReport] = useState("sales");



    useEffect(()=>{


        loadReports();


    },[]);

    const handleDateFilter = (dates)=>{

    setFilters(dates);

    };



    const loadReports = async(filters = {})=>{

        try {


            const [
                summaryData,
                salesData,
                inventoryData,
                customerData,
                profitData,
                trendData,
                topSellingData

            ] = await Promise.all([

                reportService.getSummary(),

                reportService.getSalesReport(),

                reportService.getInventoryReport(),

                reportService.getCustomerReport(),

                reportService.getProfitReport(),

                reportService.getSalesTrend(),

                reportService.getTopSellingProducts()

            ]);


            setSummary(summaryData);

            setSales(salesData);

            setInventory(inventoryData);

            setCustomers(customerData);

            setProfit(profitData);

            setSalesTrend(trendData);

            setTopSelling(topSellingData);



        } catch(error){

            console.error(
                "Failed loading reports",
                error
            );

        }

    };



    return (

        <div className="reports-page">


            <ReportHeader />

            <DateFilter onFilter={handleDateFilter} />

            <ReportCards
                summary={summary}
            />

            <ExportButtons

                sales={sales}

            />

            <Charts
                salesTrend={salesTrend}
                topSelling={topSelling}
            />

            <ReportTabs

                sales={sales}

                inventory={inventory}

                customers={customers}

                profit={profit}

                salesTrend={salesTrend}

                topSelling={topSelling}

            />


        </div>

    );


};


export default ReportsPage;