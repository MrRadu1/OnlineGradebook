import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate, useParams} from "react-router-dom";
import './StyledComponents.css';
import {compareArraysAsSet} from "@testing-library/jest-dom/dist/utils";
import ToastMessenger from "../resource/ToastMessenger";
import {ToastContainer} from "react-toastify";

export default function Home() {
    const [users, setUsers] = useState([
        {
            uuid: "",
            username: "",
            name: "",
            email: "",
            permission: "",
            idNumber: ""
        }
    ]);
    const [loading, setLoading] = useState(false);
    const {uuid} = useParams();
    let navigate = useNavigate();
    let currentUser = JSON.parse(sessionStorage.getItem("userInfo"));
    // console.log("user= ",currentUser);
    let token = sessionStorage.getItem("userGuid");
    useEffect(() => {
        if (token == null) {
            navigate('/login');
        } else {
            if (currentUser.role === "STUDENT") {
                loadUser();
            } else {
                loadUsers();
            }
        }
    }, [token]);
    const onSubmit = async e => {
        sessionStorage.clear();
        navigate('/login');
    }
    const loadUsers = async () => {
        setLoading(true);
        const result = await fetch("http://localhost:8080/api/users");
        const data = await result.json();
        setLoading(false);
        setUsers(data);
    };

    const loadUser = async () => {
        let uuid = currentUser.uuid;
        setLoading(true);
        const result = await fetch(`http://localhost:8080/api/users/${uuid}`);
        const data = await result.json();
        setLoading(false);
        sessionStorage.setItem("userInfo", JSON.stringify(data));
    };

    const deleteUser = async (uuid,username) => {
        await axios.delete(`http://localhost:8080/api/users/${uuid}`);
        ToastMessenger(`User ${username} deleted!`, "info");
        await loadUsers();
    };

    function displayContent() {
        if (token == null)
            return (
                <div></div>
            )
        let role = currentUser.role;

        if (role === "STUDENT") {
            return (
                <div className="container">
                    <div className="py-4">
                        <table className="table border shadow">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Email</th>
                                <th scope="col">Identification No</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>{currentUser.name}</td>
                                <td>{currentUser.username}</td>
                                <td>{currentUser.email}</td>
                                <td>{currentUser.uuid}</td>
                                <td>
                                    <Link
                                        className="btn btn-primary mx-2"
                                        to={`/viewuser/${currentUser.uuid}`}
                                    >
                                        View
                                    </Link>
                                    <Link
                                        className="btn btn-outline-primary mx-2"
                                        to={`/edituser/${currentUser.uuid}`}
                                    >
                                        Edit
                                    </Link>
                                    <Link
                                        className="btn btn-outline-primary mx-2"
                                        to={`/viewmarks/${currentUser.uuid}`}
                                    >
                                        View Marks
                                    </Link>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            )
        } else if (role === "ADMIN") {
            return (
                <div className="container">
                    <div className="py-4">
                        <table className="table border shadow">
                            <thead>
                            <tr>
                                <th scope="col">Reg. Number</th>
                                <th scope="col">Name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Email</th>
                                <th scope="col">Identification No</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {users.map((user, index) => (
                                <tr>
                                    <th scope="row" key={index}>
                                        {index + 1}
                                    </th>
                                    <td>{user.name}</td>
                                    <td>{user.username}</td>
                                    <td>{user.email}</td>
                                    <td>{user.uuid}</td>
                                    <td>
                                        <Link
                                            className="btn btn-primary mx-2"
                                            to={`/viewuser/${user.uuid}`}
                                        >
                                            View
                                        </Link>
                                        <Link
                                            className="btn btn-outline-primary mx-2"
                                            to={`/edituser/${user.uuid}`}
                                        >
                                            Edit
                                        </Link>
                                        <Link
                                            className="btn btn-outline-primary mx-2"
                                            to={`/addmarks/${user.uuid}`}
                                        >
                                            Add Marks
                                        </Link>
                                        <button
                                            className="btn btn-danger mx-2"
                                            onClick={() => deleteUser(user.uuid, user.username)}
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )
        } else {
            return (
                <div>
                    <div id="error-page">
                        <div className="content">
                            <h2 className="header" data-text="404">
                                Something went Wrong!
                            </h2>
                            <h4 data-text="Opps! Page not found">
                                Your account has some problems!
                            </h4>
                            <p>
                                Your account may have not a role assigned or it was deleted by an admin!
                                <br/>Please contact a teacher or and admin to solve this problem!
                            </p>
                            <button
                                className="btn btn-outline-primary mx-2"
                                type={"submit"}
                                onClick={onSubmit}>
                                Back to Login
                            </button>
                        </div>
                    </div>
                </div>
            )
        }
    }

    return (
        <div>
            <ToastContainer/>
            {loading ? (
                <div className="loader-container">
                    <div className="spinner"></div>
                </div>
            ) : (
                displayContent()
            )}
        </div>
    );
}
