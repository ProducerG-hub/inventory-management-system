import { useEffect, useState } from "react";


import "./CategoryModal.css";



const CategoryForm = ({
    category,
    onSubmit,
    onCancel
}) => {



    const initialState = {

        categoryName: "",

        description: ""

    };





    const [form,setForm] = useState(initialState);






    useEffect(()=>{


        if(category){


            setForm({

                categoryName: category.categoryName,

                description: category.description || ""

            });



        }else{


            setForm(initialState);


        }


    },[category]);







    const handleChange = (e)=>{


        const {
            name,
            value
        } = e.target;



        setForm({

            ...form,

            [name]:value

        });


    };








    const handleSubmit = (e)=>{


        e.preventDefault();



        const requestData = {


            categoryName:
                form.categoryName,


            description:
                form.description



        };



        onSubmit(requestData);



    };








    return (


        <form

            className="category-form"

            onSubmit={handleSubmit}

        >



            <div className="category-form-grid">





                <input

                    name="categoryName"

                    placeholder="Category Name"

                    value={form.categoryName}

                    onChange={handleChange}

                    required

                />






                <textarea

                    name="description"

                    placeholder="Description"

                    value={form.description}

                    onChange={handleChange}

                />





            </div>








            <div className="form-actions">





                <button

                    type="submit"

                    className="save-btn"

                >

                    Save

                </button>






                <button

                    type="button"

                    className="cancel-btn"

                    onClick={onCancel}

                >

                    Cancel

                </button>





            </div>






        </form>


    );


};



export default CategoryForm;