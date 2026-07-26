import Papa from "papaparse";


const ExportButtons = ({
    data,
    fileName = "report"
}) => {



    const exportCSV = () => {


        if(!data || data.length === 0){

            alert(
                "No data available to export"
            );

            return;

        }



        const csv = Papa.unparse(data);



        const blob = new Blob(

            [csv],

            {
                type:"text/csv;charset=utf-8;"
            }

        );



        const url =
            window.URL.createObjectURL(blob);



        const link =
            document.createElement("a");



        link.href = url;


        link.download =
            `${fileName}.csv`;



        document.body.appendChild(link);


        link.click();



        document.body.removeChild(link);


    };





    const printReport = () => {


        window.print();


    };





    return (

        <div className="export-buttons">


            <button

                onClick={exportCSV}

            >

                Export CSV

            </button>



            <button

                onClick={printReport}

            >

                Print

            </button>


        </div>

    );

};


export default ExportButtons;