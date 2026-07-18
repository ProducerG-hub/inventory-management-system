const UserSearch = ({ keyword, setKeyword }) => {

    return (

        <div className="user-search">

            <input

                type="text"

                placeholder="Search user..."

                value={keyword}

                onChange={(e) => setKeyword(e.target.value)}

            />

        </div>

    );

};

export default UserSearch;