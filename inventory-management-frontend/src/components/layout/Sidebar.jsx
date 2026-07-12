import { NavLink } from "react-router-dom";

import navigation from "../../config/constants/navigation";

import { useAuth } from "../../context/AuthContext";


const Sidebar = () => {


    const { user } = useAuth();


    const filteredMenu = navigation.filter((item)=>{

        return item.roles.includes(user.role);

    });



    return (

        <aside className="sidebar">


            <div className="sidebar-logo">

                IMS

            </div>



            <nav className="sidebar-menu">


                {
                    filteredMenu.map((item)=>{


                        const Icon = item.icon;


                        return (

                            <NavLink

                                key={item.key}

                                to={item.path}


                                className={({isActive})=>

                                    `sidebar-link ${isActive ? "active" : ""}`

                                }

                            >


                                <Icon size={20}/>


                                <span>

                                    {item.label}

                                </span>



                            </NavLink>


                        );


                    })
                }


            </nav>


        </aside>

    );


};


export default Sidebar;