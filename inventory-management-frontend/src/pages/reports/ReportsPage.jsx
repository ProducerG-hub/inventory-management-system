import { useEffect, useState } from "react";

import reportService from "../../services/reportService";
import ReportCards from "../../components/reports/ReportCards";
import ReportTabs from "../../components/reports/ReportTabs";
import ReportHeader from "../../components/reports/ReportHeader";
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
    const [filters, setFilters] = useState({ startDate: "", endDate: "" });
    const [activeReport, setActiveReport] = useState("sales");
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        loadReports(filters);
    }, [filters]);

    const getSalesParams = (currentFilters = {}) => {
        const params = {};
        if (currentFilters.startDate) params.startDate = currentFilters.startDate;
        if (currentFilters.endDate) params.endDate = currentFilters.endDate;
        return params;
    };

    const loadReports = async (currentFilters = {}) => {
        try {
            setIsLoading(true);
            const [summaryData, salesData, inventoryData, customerData, profitData, trendData, topSellingData] = await Promise.all([
                reportService.getSummary(),
                reportService.getSalesReport(getSalesParams(currentFilters)),
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
        } catch (error) {
            console.error("Failed loading reports", error);
        } finally {
            setIsLoading(false);
        }
    };

    const reportData = { sales, inventory, customers, profit };
    const activeReportConfig = getReportConfig(activeReport);
    const activeReportData = reportData[activeReport] || [];

    return (
        <div className="reports-page">
            <ReportHeader />
            <DateFilter onFilter={setFilters} />

            {isLoading && !summary ? (
                <div className="reports-loading" role="status" aria-live="polite">
                    <span className="reports-loading__spinner" aria-hidden="true" />
                </div>
            ) : (
                <>
                    <ReportCards summary={summary} />

                    <section className="report-workspace" aria-label={activeReportConfig.title}>
                        <div className="report-workspace__toolbar">
                            <div>
                                <h2>{activeReportConfig.title}</h2>
                                <p>{activeReportData.length} {activeReportData.length === 1 ? "record" : "records"}</p>
                            </div>
                            <ExportButtons data={activeReportData} reportConfig={activeReportConfig} />
                        </div>

                        <ReportTabs
                            tabs={REPORT_TABS}
                            activeTab={activeReport}
                            onTabChange={setActiveReport}
                            sales={sales}
                            inventory={inventory}
                            customers={customers}
                            profit={profit}
                        />
                    </section>

                    <Charts salesTrend={salesTrend} topSelling={topSelling} />
                </>
            )}
        </div>
    );
};

export default ReportsPage;
