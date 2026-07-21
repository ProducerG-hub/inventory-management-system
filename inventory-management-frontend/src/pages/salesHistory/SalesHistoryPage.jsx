import { useEffect, useState } from "react";

import saleService from "../../services/saleService";
import stockMovementService from "../../services/stockMovementService";

import SalesTabs from "../../components/salesHistory/SalesTabs";
import SalesSearch from "../../components/salesHistory/SalesSearch";
import SalesTable from "../../components/salesHistory/SalesTable";
import SalesPagination from "../../components/salesHistory/SalesPagination";
import EmptyState from "../../components/salesHistory/EmptyState";
import SalesSkeleton from "../../components/salesHistory/SalesSkeleton";

import StockMovementHeader from "../../components/stockMovements/StockMovementHeader";
import MovementSummaryCards from "../../components/stockMovements/MovementSummaryCards";
import MovementSearch from "../../components/stockMovements/MovementSearch";
import MovementTable from "../../components/stockMovements/MovementTable";

import ReceiptPreview from "../../components/sales/ReceiptPreview";

import "./SalesHistory.css";

const SalesHistoryPage = () => {
    const [activeTab, setActiveTab] = useState("sales");

    // SALES STATES
    const [sales, setSales] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [keyword, setKeyword] = useState("");

    // MOVEMENT STATES
    const [movements, setMovements] = useState([]);
    const [movementPage, setMovementPage] = useState(0);
    const [movementTotalPages, setMovementTotalPages] = useState(0);
    const [movementKeyword, setMovementKeyword] = useState("");
    const [movementType, setMovementType] = useState("");
    
    const [movementStats, setMovementStats] = useState({
        totalMovements: 0,
        totalIn: 0,
        totalOut: 0,
        todayMovements: 0
    });

    const [loading, setLoading] = useState(false);
    const [receipt, setReceipt] = useState(null);

    /*
        LOAD SALES
    */
    useEffect(() => {
        const timer = setTimeout(() => {
            loadSales();
        }, 300);
        return () => clearTimeout(timer);
    }, [page, keyword]);

    const loadSales = async () => {
        try {
            setLoading(true);
            let response;
            if (keyword.trim()) {
                response = await saleService.searchSales({
                    keyword,
                    page,
                    size: 10,
                    sortBy: "saleId",
                    sortDir: "desc"
                });
            } else {
                response = await saleService.getSales({
                    page,
                    size: 10,
                    sortBy: "saleId",
                    sortDir: "desc"
                });
            }

            setSales(response.content || []);
            setTotalPages(response.totalPages || 0);
        } catch (error) {
            console.error(error);
            setSales([]);
        } finally {
            setLoading(false);
        }
    };

    /*
        RESET PAGE WHEN SEARCH
    */
    useEffect(() => {
        setPage(0);
    }, [keyword]);

    useEffect(() => {
        setMovementPage(0);
    }, [movementKeyword, movementType]);

    /*
        LOAD STOCK MOVEMENTS & STATS
    */
    useEffect(() => {
        if (activeTab === "movements") {
            const timer = setTimeout(() => {
                loadMovements();
                loadMovementStats(); 
            }, 300);
            return () => clearTimeout(timer);
        }
    }, [activeTab, movementPage, movementKeyword, movementType]);

    const loadMovements = async () => {
        try {
            setLoading(true);
            let response;

            const params = {
                page: movementPage,
                size: 10,
                sortBy: "movementId",
                sortDir: "desc",
                ...(movementType && { movementType })  
            };

            if (movementKeyword.trim()) {
                params.keyword = movementKeyword.trim();
                response = await stockMovementService.searchMovements(params);
            } else {
                response = await stockMovementService.getMovements(params);
            }

            setMovements(response.content || []);
            setMovementTotalPages(response.totalPages || 0);
        } catch (error) {
            console.error(error);
            setMovements([]);
        } finally {
            setLoading(false);
        }
    };

    const loadMovementStats = async () => {
        try {
            const data = await stockMovementService.getStats();
            setMovementStats(data);
        } catch (error) {
            console.error("Failed to load movement stats:", error);
        }
    };

    const openReceipt = async (id) => {
        try {
            const data = await saleService.getReceipt(id);
            setReceipt(data);
        } catch (error) {
            console.error(error);
        }
    };

    const filteredMovements = movements.filter((item) => {
        
        const matchesType = movementType === "" || item.movementType === movementType;
        
        
        const matchesKeyword = movementKeyword === "" || 
            (item.product && item.product.name && item.product.name.toLowerCase().includes(movementKeyword.toLowerCase()));

        return matchesType && matchesKeyword;
    });

    return (
        <div className="sales-history-page">
            <div className="sales-history-header">
                <div>
                    <h2>
                        {activeTab === "sales" ? "Sales History" : "Stock Movements"}
                    </h2>
                    <p>
                        {activeTab === "sales"
                            ? "View completed sales and receipts."
                            : "Track stock IN and OUT movements."}
                    </p>
                </div>
            </div>

            <SalesTabs
                activeTab={activeTab}
                setActiveTab={setActiveTab}
            />

            {activeTab === "sales" && (
                <>
                    <SalesSearch
                        keyword={keyword}
                        setKeyword={setKeyword}
                        totalSales={sales.length}
                    />

                    {loading ? (
                        <SalesSkeleton />
                    ) : sales.length === 0 ? (
                        <EmptyState
                            title="No Sales Found"
                            message="Sales will appear here after completing transactions."
                        />
                    ) : (
                        <SalesTable
                            sales={sales}
                            onViewReceipt={openReceipt}
                        />
                    )}

                    <SalesPagination
                        page={page}
                        totalPages={totalPages}
                        setPage={setPage}
                    />
                </>
            )}

            {activeTab === "movements" && (
                <>
                    <StockMovementHeader />
                    <MovementSummaryCards
                        totalMovements={movementStats.totalMovements}
                        totalIn={movementStats.totalIn}
                        totalOut={movementStats.totalOut}
                        todayMovements={movementStats.todayMovements}
                    />

                    <MovementSearch
                        keyword={movementKeyword}
                        setKeyword={setMovementKeyword}
                        movementType={movementType}
                        setMovementType={setMovementType}
                    />

                    
                    {loading ? (
                        <SalesSkeleton />
                    ) : filteredMovements.length === 0 ? (
                        <EmptyState
                            title="No Stock Movements"
                            message="Stock IN and OUT records will appear here based on your search."
                        />
                    ) : (
                        <MovementTable movements={filteredMovements} />
                    )}

                    <SalesPagination
                        page={movementPage}
                        totalPages={movementTotalPages}
                        setPage={setMovementPage}
                    />
                </>
            )}

            {receipt && (
                <ReceiptPreview
                    receipt={receipt}
                    onClose={() => setReceipt(null)}
                />
            )}
        </div>
    );
};

export default SalesHistoryPage;