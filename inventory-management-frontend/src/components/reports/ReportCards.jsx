const ReportCards = ({summary}) => {


    if(!summary){

        return null;

    }



    const cards = [

        {
            title:"Total Sales",
            value: summary.totalSales
        },

        {
            title:"Total Revenue",
            value:
            `TZS ${summary.totalRevenue.toLocaleString()}`
        },

        {
            title:"Total Profit",
            value:
            `TZS ${summary.totalProfit.toLocaleString()}`
        },

        {
            title:"Low Stock Products",
            value: summary.lowStockProducts
        }

    ];



    return (

        <div className="report-cards">


            {
                cards.map((card,index)=>(

                    <div 
                        className="report-card"
                        key={index}
                    >

                        <h3>
                            {card.title}
                        </h3>


                        <h2>
                            {card.value}
                        </h2>


                    </div>


                ))
            }


        </div>

    );

};


export default ReportCards;