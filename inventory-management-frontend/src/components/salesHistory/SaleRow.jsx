const SaleRow = ({ sale, onViewReceipt }) => {

    const formatCurrency = (amount) => {

        return new Intl.NumberFormat(
            "en-TZ",
            {
                style: "currency",
                currency: "TZS",
                minimumFractionDigits: 0
            }
        ).format(amount);

    };

    return (

        <tr>

            <td>

                #{sale.saleId}

            </td>

            <td>

                {
                    new Date(
                        sale.saleDate
                    ).toLocaleString()
                }

            </td>

            <td>

                {sale.userFullName}

            </td>

            <td>

                {sale.customerName}

            </td>

            <td>

                {sale.items?.length ?? 0}

            </td>

            <td>

                {formatCurrency(sale.totalAmount)}

            </td>

            <td>

                <button
                    className="view-btn"
                    onClick={() => onViewReceipt(sale.saleId)}
                >

                    View Receipt

                </button>

            </td>

        </tr>

    );

};

export default SaleRow;