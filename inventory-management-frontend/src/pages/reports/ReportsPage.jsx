import { useEffect, useState } from "react";

import reportService from "../../services/reportService";

import ReportCards from "../../components/reports/ReportCards";
import ReportTabs from "../../components/reports/ReportTabs";
import Charts from "../../components/reports/Charts";
import DateFilter from "../../components/reports/DateFilter";
import ExportButtons from "../../components/reports/ExportButtons";
import { getReportConfig, REPORT_TABS } from "../../components/reports/reportConfig";

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


        loadReports(filters);


    },[filters]);

    const handleDateFilter = (dates)=>{

    setFilters(dates);

    };

    const handleTabChange = (reportKey) => {

        setActiveReport(reportKey);

    };

    const getSalesParams = (currentFilters = {}) => {

        const params = {};

        if (currentFilters.startDate) {
            params.startDate = currentFilters.startDate;
        }

        if (currentFilters.endDate) {
            params.endDate = currentFilters.endDate;
        }

        return params;

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

                reportService.getSalesReport(
                    getSalesParams(filters)
                ),

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

    const reportData = {
        sales,
        inventory,
        customers,
        profit
    };

    const activeReportConfig = getReportConfig(activeReport);
    const activeReportData = reportData[activeReport] || [];



    return (

        <div className="reports-page">


            <DateFilter onFilter={handleDateFilter} />

            <ReportCards
                summary={summary}
            />

            <ExportButtons
                data={activeReportData}
                reportConfig={activeReportConfig}

            />


            <ReportTabs
                tabs={REPORT_TABS}
                activeTab={activeReport}
                onTabChange={handleTabChange}
                sales={sales}
                inventory={inventory}
                customers={customers}
                profit={profit}

            />

            
            <Charts
                salesTrend={salesTrend}
                topSelling={topSelling}
            />


        </div>

    );


};


export default ReportsPage;