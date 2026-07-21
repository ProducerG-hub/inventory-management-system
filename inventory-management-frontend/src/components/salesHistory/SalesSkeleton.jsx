const SalesSkeleton = () => {

    return (

        <div className="sales-skeleton">

            <div className="skeleton-header"></div>

            {

                [...Array(8)].map((_,index)=>(

                    <div
                        key={index}
                        className="skeleton-row"
                    >

                        <span></span>

                        <span></span>

                        <span></span>

                        <span></span>

                        <span></span>

                        <span></span>

                    </div>

                ))

            }

        </div>

    );

};

export default SalesSkeleton;