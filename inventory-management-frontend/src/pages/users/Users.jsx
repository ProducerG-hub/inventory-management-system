import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import toast from "react-hot-toast";

import userService from "../../services/userService";

import UserSearch from "../../components/users/UserSearch";
import UserTable from "../../components/users/UserTable";
import UserPagination from "../../components/users/UserPagination";
import UserModal from "../../components/users/UserModal";
import ConfirmModal from "../../components/common/ConfirmModal";

import "./Users.css";

const Users = () => {

    const { user } = useAuth();

    const [users, setUsers] = useState([]);
    const [keyword, setKeyword] = useState("");
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const [activeTab, setActiveTab] = useState("active");

    const [showModal, setShowModal] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [userToDelete, setUserToDelete] = useState(null);



    const loadUsers = async () => {

        try {

            const params = {
                page,
                size: 10,
                sortBy: "userId",
                sortDir: "asc"
            };

            let response;

            if (keyword.trim()) {

                response = await userService.searchUsers({

                    ...params,

                    keyword,

                    active: activeTab === "active"

                });

            } else {

                if (activeTab === "active") {

                    response = await userService.getActiveUsers(params);

                } else {

                    response = await userService.getInactiveUsers(params);

                }

            }

            setUsers(response.content);
            setTotalPages(response.totalPages);

        } catch (error) {

            console.error(error);

            toast.error("Failed to load users");

        }

    };



    useEffect(() => {

        loadUsers();

    }, [page, keyword, activeTab]);



    const handleSave = async (data) => {

        const loading = toast.loading(

            selectedUser

                ? "Updating user..."

                : "Creating user..."

        );

        try {

            if (selectedUser) {

                await userService.updateUser(

                    selectedUser.userId,

                    data

                );

                toast.dismiss(loading);

                toast.success(

                    "User updated successfully"

                );

            } else {

                await userService.createUser(data);

                toast.dismiss(loading);

                toast.success(

                    "User created successfully"

                );

            }

            setShowModal(false);

            setSelectedUser(null);

            loadUsers();

        } catch (error) {

            console.error(error);

            toast.dismiss(loading);

            toast.error("Operation failed");

        }

    };



    const handleDelete = async () => {

        if (!userToDelete) return;

        const loading = toast.loading(

            "Deactivating user..."

        );

        try {

            await userService.deleteUser(

                userToDelete.userId

            );

            toast.dismiss(loading);

            toast.success(

                "User deactivated successfully"

            );

            setShowDeleteModal(false);

            setUserToDelete(null);

            loadUsers();

        } catch (error) {

            console.error(error);

            toast.dismiss(loading);

            toast.error("Failed to deactivate user");

        }

    };



    const handleRestore = async (selected) => {

        const loading = toast.loading(

            "Restoring user..."

        );

        try {

            await userService.restoreUser(

                selected.userId

            );

            toast.dismiss(loading);

            toast.success(

                "User restored successfully"

            );

            loadUsers();

        } catch (error) {

            console.error(error);

            toast.dismiss(loading);

            toast.error("Restore failed");

        }

    };



    return (

        <div className="users-page">

            <div className="users-header">

                <div>

                    <h2>

                        Users

                    </h2>

                    <p>

                        Manage system users

                    </p>

                </div>

                {
                    user?.role === "ADMIN" && (

                        <button

                            className="primary-btn"

                            onClick={() => {

                                setSelectedUser(null);

                                setShowModal(true);

                            }}

                        >

                            + Add User

                        </button>

                    )
                }

            </div>



            {
                user?.role === "ADMIN" && (

                    <div className="users-tabs">

                        <button

                            className={

                                activeTab === "active"

                                    ? "tab-btn active"

                                    : "tab-btn"

                            }

                            onClick={() => {

                                setPage(0);

                                setActiveTab("active");

                            }}

                        >

                            Active Users

                        </button>

                        <button

                            className={

                                activeTab === "inactive"

                                    ? "tab-btn active"

                                    : "tab-btn"

                            }

                            onClick={() => {

                                setPage(0);

                                setActiveTab("inactive");

                            }}

                        >

                            Inactive Users

                        </button>

                    </div>

                )
            }



            <UserSearch

                keyword={keyword}

                setKeyword={(value) => {

                    setPage(0);

                    setKeyword(value);

                }}

            />



            <UserTable

                users={users}

                activeTab={activeTab}

                onEdit={(selected) => {

                    setSelectedUser(selected);

                    setShowModal(true);

                }}

                onDelete={(selected) => {

                    setUserToDelete(selected);

                    setShowDeleteModal(true);

                }}

                onRestore={handleRestore}

            />



            <UserPagination

                currentPage={page}

                totalPages={totalPages}

                setPage={setPage}

            />



            {

                showModal && (

                    <UserModal

                        user={selectedUser}

                        onClose={() => {

                            setShowModal(false);

                            setSelectedUser(null);

                        }}

                        onSubmit={handleSave}

                    />

                )

            }



            <ConfirmModal

                isOpen={showDeleteModal}

                title="Deactivate User"

                message={`Are you sure you want to deactivate "${userToDelete?.fullName}"?`}

                confirmText="Deactivate"

                cancelText="Cancel"

                onConfirm={handleDelete}

                onCancel={() => {

                    setShowDeleteModal(false);

                    setUserToDelete(null);

                }}

            />

        </div>

    );

};

export default Users;