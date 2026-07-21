import SaleRow from "./SaleRow";

const SalesTable=({

    sales,

    loading,

    onViewReceipt

})=>{

    return(

        <div className="sales-card">

            <table>

                <thead>

                    <tr>

                        <th>Receipt</th>

                        <th>Date</th>

                        <th>Cashier</th>

                        <th>Customer</th>

                        <th>Items</th>

                        <th>Total</th>

                        <th></th>

                    </tr>

                </thead>

                <tbody>

                    {

                        loading ?

                        (

                            <tr>

                                <td

                                    colSpan={7}

                                    style={{

                                        textAlign:"center",

                                        padding:"40px"

                                    }}

                                >

                                    Loading...

                                </td>

                            </tr>

                        )

                        :

                        sales.length===0 ?

                        (

                            <tr>

                                <td

                                    colSpan={7}

                                    style={{

                                        textAlign:"center",

                                        padding:"40px"

                                    }}

                                >

                                    No Sales Found

                                </td>

                            </tr>

                        )

                        :

                        sales.map(sale=>

                            <SaleRow

                                key={sale.saleId}

                                sale={sale}

                                onViewReceipt={onViewReceipt}

                            />

                        )

                    }

                </tbody>

            </table>

        </div>

    );

};

export default SalesTable;