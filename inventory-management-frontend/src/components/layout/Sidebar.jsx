import { NavLink } from "react-router-dom";

import navigation from "../../config/constants/navigation";

import { useAuth } from "../../context/AuthContext";
import logo from "../../assets/images/mlue_logo(white).png";


const Sidebar = ({ isOpen = false, onNavigate }) => {


    const { user } = useAuth();


    const filteredMenu = navigation.filter((item)=>{

        return item.roles.includes(user.role);

    });



    return (

        <aside className={`sidebar ${isOpen ? "is-open" : ""}`}>


            <div className="sidebar-logo">

                <img src={logo} alt="Logo" className="logo-img" />

            </div>
            <hr />



            <nav className="sidebar-menu">


                {
                    filteredMenu.map((item)=>{


                        const Icon = item.icon;


                        return (

                            <NavLink

                                key={item.key}

                                to={item.path}
                                onClick={onNavigate}


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