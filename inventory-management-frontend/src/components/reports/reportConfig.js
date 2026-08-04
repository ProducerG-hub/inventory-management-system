export const REPORT_TABS = [
    {
        key: "sales",
        label: "Sales",
        fileName: "sales-report",
        title: "Sales Report",
        columns: [
            {
                label: "Invoice",
                value: (row) => `#${row.saleId}`
            },
            {
                label: "Customer",
                value: (row) => row.customerName
            },
            {
                label: "Cashier",
                value: (row) => row.cashierName
            },
            {
                label: "Items",
                value: (row) => row.totalItems
            },
            {
                label: "Amount",
                value: (row) => `TZS ${row.totalAmount?.toLocaleString?.() ?? row.totalAmount ?? "0"}`
            },
            {
                label: "Date",
                value: (row) => new Date(row.saleDate).toLocaleDateString()
            }
        ]
    },
    {
        key: "inventory",
        label: "Inventory",
        fileName: "inventory-report",
        title: "Inventory Report",
        columns: [
            {
                label: "Product",
                value: (row) => row.productName
            },
            {
                label: "Category",
                value: (row) => row.categoryName
            },
            {
                label: "Supplier",
                value: (row) => row.supplierName
            },
            {
                label: "Buying Price",
                value: (row) => `TZS ${row.buyingPrice?.toLocaleString?.() ?? row.buyingPrice ?? "0"}`
            },
            {
                label: "Selling Price",
                value: (row) => `TZS ${row.sellingPrice?.toLocaleString?.() ?? row.sellingPrice ?? "0"}`
            },
            {
                label: "Quantity",
                value: (row) => row.quantity
            },
            {
                label: "Stock Value",
                value: (row) => `TZS ${row.stockValue?.toLocaleString?.() ?? row.stockValue ?? "0"}`
            },
            {
                label: "Status",
                value: (row) => row.stockStatus
            }
        ]
    },
    {
        key: "customers",
        label: "Customers",
        fileName: "customer-report",
        title: "Customer Report",
        columns: [
            {
                label: "Customer",
                value: (row) => row.customerName
            },
            {
                label: "Phone",
                value: (row) => row.phone
            },
            {
                label: "Total Orders",
                value: (row) => row.totalOrders
            },
            {
                label: "Total Spent",
                value: (row) => `TZS ${row.totalSpent?.toLocaleString?.() ?? row.totalSpent ?? "0"}`
            },
            {
                label: "Last Purchase",
                value: (row) => row.lastPurchaseDate ? new Date(row.lastPurchaseDate).toLocaleDateString() : "No purchase"
            }
        ]
    },
    {
        key: "profit",
        label: "Profit",
        fileName: "profit-report",
        title: "Profit Report",
        columns: [
            {
                label: "Product",
                value: (row) => row.productName
            },
            {
                label: "Quantity Sold",
                value: (row) => row.totalQuantitySold
            },
            {
                label: "Revenue",
                value: (row) => `TZS ${row.totalRevenue?.toLocaleString?.() ?? row.totalRevenue ?? "0"}`
            },
            {
                label: "Cost",
                value: (row) => `TZS ${row.totalCost?.toLocaleString?.() ?? row.totalCost ?? "0"}`
            },
            {
                label: "Profit",
                value: (row) => `TZS ${row.totalProfit?.toLocaleString?.() ?? row.totalProfit ?? "0"}`
            }
        ]
    }
];

export const REPORT_TAB_KEYS = REPORT_TABS.map((tab) => tab.key);

export const getReportConfig = (reportKey) =>
    REPORT_TABS.find((tab) => tab.key === reportKey) ?? REPORT_TABS[0];
