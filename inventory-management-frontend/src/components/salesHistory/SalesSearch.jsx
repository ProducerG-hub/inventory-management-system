const SalesSearch = ({

    keyword,

    setKeyword,

    totalSales

}) => {

    return (

        <div className="sales-search">

            <div className="sales-search-input">

                <input

                    type="text"

                    value={keyword}

                    placeholder="Search customer, cashier or receipt..."

                    onChange={(e) =>

                        setKeyword(e.target.value)

                    }

                />

            </div>

            <div className="sales-search-info">

                Showing

                <strong>

                    {" "}{totalSales}{" "}

                </strong>

                sales

            </div>

        </div>

    );

};

export default SalesSearch;