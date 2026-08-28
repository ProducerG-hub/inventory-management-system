import StatCard from "../dashboard/StatCard";

const ReportCards = ({ summary }) => {
    if (!summary) {
        return null;
    }

    const cards = [
        { title: "Total Sales", value: summary.totalSales },
        { title: "Total Revenue", value: `TZS ${summary.totalRevenue.toLocaleString()}` },
        { title: "Total Profit", value: `TZS ${summary.totalProfit.toLocaleString()}` },
        { title: "Low Stock Products", value: summary.lowStockProducts }
    ];

    return (
        <div className="report-cards stats-grid">
            {cards.map((card) => (
                <StatCard key={card.title} title={card.title} value={card.value} />
            ))}
        </div>
    );
};

export default ReportCards;
