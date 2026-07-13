import {useEffect, useState} from "react";


import {useAuth} from "../../context/AuthContext";


import dashboardService from "../../services/dashboardService";


import DashboardHeader from "../../components/dashboard/DashboardHeader";

import StatCard from "../../components/dashboard/StatCard";

import RecentProductsTable from "../../components/dashboard/RecentProductsTable";

import LowStockTable from "../../components/dashboard/LowStockTable";


import "./Dashboard.css";



const Dashboard =()=>{


    const {user}=useAuth();



    const [dashboard,setDashboard]=useState({

        summary:{},

        recentProducts:[],

        lowStockProducts:[]

    });



    useEffect(()=>{


        loadDashboard();


    },[]);




    const loadDashboard=async()=>{


        try{


            const data = await dashboardService.getDashboard();


            setDashboard(data);



        }catch(error){


            console.error(error);


        }


    };




    const summary = dashboard.summary;




    return (

        <div className="dashboard">



            <DashboardHeader/>




            <div className="stats-grid">


                <StatCard

                    title="Products"

                    value={summary.totalProducts || 0}

                />



                <StatCard

                    title="Categories"

                    value={summary.totalCategories || 0}

                />



                <StatCard

                    title="Suppliers"

                    value={summary.totalSuppliers || 0}

                />



                {

                    user?.role==="ADMIN" &&

                    <StatCard

                        title="Users"

                        value={summary.totalUsers || 0}

                    />

                }



                <StatCard

                    title="Low Stock"

                    value={summary.lowStockProducts || 0}

                />



            </div>





            <div className="dashboard-content">


                <RecentProductsTable

                    products={dashboard.recentProducts}

                />



                <LowStockTable

                    products={dashboard.lowStockProducts}

                />



            </div>



        </div>

    );


};



export default Dashboard;