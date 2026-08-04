import {
    LineChart,
    Line,
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer
} from "recharts";


const Charts = ({
    salesTrend,
    topSelling
}) => {


    return (

        <div className="charts-container">


            {/* SALES TREND */}

            <div className="chart-card">


                <h3>
                    Sales Trend
                </h3>



                {
                    salesTrend &&
                    salesTrend.length > 0 &&

                    <div className="chart-frame">

                        <ResponsiveContainer
                            width="100%"
                            height="100%"
                        >

                            <LineChart
                                data={salesTrend}
                            >


                            <CartesianGrid
                                strokeDasharray="3 3"
                            />


                            <XAxis
                                dataKey="date"
                            />


                            <YAxis />


                            <Tooltip />


                            <Line

                                type="monotone"

                                dataKey="totalRevenue"

                                strokeWidth={3}

                            />


                            </LineChart>


                        </ResponsiveContainer>

                    </div>

                }

                {
                    (!salesTrend || salesTrend.length === 0) &&

                    <div className="empty-report chart-empty-state">
                        No sales trend data available
                    </div>
                }


            </div>





            {/* TOP SELLING PRODUCTS */}


            <div className="chart-card">


                <h3>
                    Top Selling Products
                </h3>



                {
                    topSelling &&
                    topSelling.length > 0 &&

                    <div className="chart-frame">

                        <ResponsiveContainer
                            width="100%"
                            height="100%"
                        >


                            <BarChart
                                data={topSelling}
                            >


                            <CartesianGrid
                                strokeDasharray="3 3"
                            />


                            <XAxis

                                dataKey="productName"

                            />


                            <YAxis />


                            <Tooltip />



                            <Bar

                                dataKey="totalQuantitySold"

                            />


                            </BarChart>


                        </ResponsiveContainer>

                    </div>

                }

                {
                    (!topSelling || topSelling.length === 0) &&

                    <div className="empty-report chart-empty-state">
                        No top selling product data available
                    </div>
                }


            </div>


        </div>

    );

};


export default Charts;