import Papa from "papaparse";

const escapeHtml = (value) =>
    String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;");

const buildRows = (data = [], columns = []) =>
    data.map((row) =>
        columns.reduce((accumulator, column) => {
            accumulator[column.label] = column.value(row);
            return accumulator;
        }, {})
    );

const ExportButtons = ({
    data,
    reportConfig
}) => {

    const rows = buildRows(data, reportConfig?.columns ?? []);



    const exportCSV = () => {


        if(!rows || rows.length === 0){

            alert(
                "No data available to export"
            );

            return;

        }



    const csv = Papa.unparse(rows);



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
            `${reportConfig?.fileName ?? "report"}.csv`;



        document.body.appendChild(link);


        link.click();



        document.body.removeChild(link);


    };





    const printReport = () => {

        if(!rows || rows.length === 0){

            alert(
                "No data available to print"
            );

            return;

        }

        const printWindow = window.open(
            "",
            "_blank",
            "width=1200,height=800"
        );

        if(!printWindow){

            alert(
                "Unable to open print preview"
            );

            return;

        }

        const headerCells = (reportConfig?.columns ?? [])
            .map((column) => `<th>${escapeHtml(column.label)}</th>`)
            .join("");

        const bodyRows = rows
            .map((row) => {
                const cells = Object.values(row)
                    .map((value) => `<td>${escapeHtml(value)}</td>`)
                    .join("");

                return `<tr>${cells}</tr>`;
            })
            .join("");

        printWindow.document.write(`
            <!doctype html>
            <html>
                <head>
                    <title>${escapeHtml(reportConfig?.title ?? "Report")}</title>
                    <style>
                        :root {
                            --primary-color: #2563EB;
                            --secondary-color: #1E293B;
                            --border-color: #E5E7EB;
                            --text-primary: #1F2937;
                            --text-secondary: #6B7280;
                            --surface-color: #FFFFFF;
                        }

                        * {
                            box-sizing: border-box;
                        }

                        body {
                            font-family: Arial, sans-serif;
                            margin: 0;
                            padding: 32px;
                            color: var(--text-primary);
                            background: #fff;
                        }

                        h1 {
                            margin: 0 0 8px;
                            color: var(--secondary-color);
                        }

                        p {
                            margin: 0 0 24px;
                            color: var(--text-secondary);
                        }

                        table {
                            width: 100%;
                            border-collapse: collapse;
                        }

                        th, td {
                            border: 1px solid var(--border-color);
                            padding: 12px 14px;
                            text-align: left;
                            vertical-align: top;
                        }

                        th {
                            background: var(--primary-color);
                            color: #fff;
                        }
                    </style>
                </head>
                <body>
                    <h1>${escapeHtml(reportConfig?.title ?? "Report")}</h1>
                    <p>Generated on ${new Date().toLocaleString()}</p>
                    <table>
                        <thead><tr>${headerCells}</tr></thead>
                        <tbody>${bodyRows}</tbody>
                    </table>
                </body>
            </html>
        `);

        printWindow.document.close();
        printWindow.focus();

        printWindow.addEventListener(
            "afterprint",
            () => printWindow.close(),
            { once: true }
        );

        printWindow.print();

        setTimeout(() => {
            printWindow.close();
        }, 1000);


    };





    return (

        <div className="export-buttons mb-4">


            <button
                type="button"
                className="export-button export-button--primary"
                disabled={!rows || rows.length === 0}

                onClick={exportCSV}

            >

                Export CSV

            </button>



            <button
                type="button"
                className="export-button export-button--secondary"
                disabled={!rows || rows.length === 0}

                onClick={printReport}

            >

                Print

            </button>


        </div>

    );

};


export default ExportButtons;