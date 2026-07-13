import { useState } from "react";
import "./Login.css";


const Login = () => {


    const [email, setEmail] = useState("");

    const [password, setPassword] = useState("");



    const handleSubmit = (e) => {

        e.preventDefault();


        console.log({
            email,
            password
        });

    };



    return (

        <div className="login-page">


           <div className="login-brand">

                <div className="brand-content">

                    <div className="brand-logo">
                        IMS
                    </div>


                    <h2>
                        Inventory Management System
                    </h2>


                    <p>
                        Smart inventory control solution for managing
                        products, suppliers and stock efficiently.
                    </p>


                </div>


                <div className="brand-circle circle-one"></div>

                <div className="brand-circle circle-two"></div>


            </div>




            <div className="login-container">


                <div className="login-card">


                    <h2>
                        Welcome Back
                    </h2>


                    <p>
                        Login to continue
                    </p>



                    <form onSubmit={handleSubmit}>


                        <div className="form-group">

                            <label>
                                Email
                            </label>


                            <input

                                type="email"

                                value={email}

                                onChange={(e)=>setEmail(e.target.value)}

                                placeholder="Enter your email"

                            />


                        </div>




                        <div className="form-group">

                            <label>
                                Password
                            </label>


                            <input

                                type="password"

                                value={password}

                                onChange={(e)=>setPassword(e.target.value)}

                                placeholder="Enter your password"

                            />


                        </div>




                        <button className="login-btn">

                            Login

                        </button>



                    </form>



                </div>


            </div>


        </div>

    );

};


export default Login;