import axios from "axios";
import React, {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ToastMessenger from "../resource/ToastMessenger";

export default function Register() {
    let navigate = useNavigate();

    const [error, setError] = useState({
        message:"",
        timestamp:"",
        httpRequest:"",
        exceptionType:""
    });
    const [loading, setLoading] = useState(false);
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        username: "",
        email: "",
        password: "",
        repeatPassword: "",
        address: {
            country: "",
            city: "",
            street: "",
            number: "",
        },
        cnp: ""
    });

    const onInputChange = (e) => {
        if (Object.keys(user.address).includes(e.target.name)) {
            setUser({
                ...user,
                address: { ...user.address, [e.target.name]: e.target.value },
            });
        } else {
            setUser({
                ...user,
                [e.target.name]: e.target.value,
            });
        }
    };

    async function registerUser(newUser) {
        setLoading(true);
        const result = await fetch("http://localhost:8080/auth/signup", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        });
        const data = await result.json();
        setLoading(false);
        if (result.ok) {
            ToastMessenger("User registered!", "success")
            navigate('/');
        } else {
            console.log("data= ", data);
            setError(data);
            ToastMessenger(data.message,"error");
        }
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        if(checkPasswords()) {
            await registerUser(user);
        }
    };

    function checkPasswords(){
        if(user.password !== user.repeatPassword){
            ToastMessenger("Passwords don't match!", "error");
            return 0;
        }
        return 1;
    }

    return (
        <div>
            <ToastContainer />
            {loading ?
                (
                    <div className="loader-container">
                        <div className="spinner"></div>
                    </div>
                ) : (
                    <div className="container">
                        <div className="row">
                            <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                                <h2 className="text-center m-4">Register User</h2>

                                <form onSubmit={(e) => onSubmit(e)}>
                                    <div className="mb-3">
                                        <label htmlFor="First Name" className="form-label">
                                            First Name
                                        </label>
                                        <input
                                            type={"text"}
                                            className="form-control"
                                            placeholder="Enter your first name"
                                            name="firstName"
                                            value={user.firstName}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="last Name" className="form-label">
                                            Last Name
                                        </label>
                                        <input
                                            type={"text"}
                                            className="form-control"
                                            placeholder="Enter your last name"
                                            name="lastName"
                                            value={user.lastName}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="Username" className="form-label">
                                            Username
                                        </label>
                                        <input
                                            type={"text"}
                                            className="form-control"
                                            placeholder="Enter your username"
                                            name="username"
                                            value={user.username}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="Email" className="form-label">
                                            E-mail
                                        </label>
                                        <input
                                            type={"text"}
                                            className="form-control"
                                            placeholder="Enter your e-mail address"
                                            name="email"
                                            value={user.email}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="Password" className="form-label">
                                            Password
                                        </label>
                                        <input
                                            type={"password"}
                                            className="form-control"
                                            placeholder="Enter your password"
                                            name="password"
                                            value={user.password}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="Re-enter password" className="form-label">
                                            Re-enter password
                                        </label>
                                        <input
                                            type={"password"}
                                            className="form-control"
                                            placeholder="Re-enter your password"
                                            name="repeatPassword"
                                            value={user.repeatPassword}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>

                                    <div className="mb-3" >
                                        <label htmlFor="Address" className="form-label">
                                            Address
                                        </label>
                                        <div className= "address-input">
                                            <input
                                                type={"text"}
                                                className="form-control"
                                                placeholder="Country"
                                                name="country"
                                                value={user.address.country}
                                                onChange={(e) => onInputChange(e)}
                                            />

                                            <input
                                                type={"text"}
                                                className="form-control"
                                                placeholder="City"
                                                name="city"
                                                value={user.address.city}
                                                onChange={(e) => onInputChange(e)}
                                            />

                                            <input
                                                type={"text"}
                                                className="form-control"
                                                placeholder="Street"
                                                name="street"
                                                value={user.address.street}
                                                onChange={(e) => onInputChange(e)}
                                            />

                                            <input
                                                type={"text"}
                                                className="form-control"
                                                placeholder="Number"
                                                name="number"
                                                value={user.address.number}
                                                onChange={(e) => onInputChange(e)}
                                            />
                                        </div>
                                    </div>

                                    <div className="mb-3">
                                        <label htmlFor="Personal Identification Number" className="form-label">
                                            Personal Identification Number
                                        </label>
                                        <input
                                            type={"text"}
                                            className="form-control"
                                            placeholder="Enter your Personal ID Number"
                                            name="cnp"
                                            value={user.cnp}
                                            onChange={(e) => onInputChange(e)}
                                        />
                                    </div>
                                    <button type="submit" className="btn btn-outline-primary">
                                        Submit
                                    </button>
                                    <Link className="btn btn-outline-danger mx-2" to="/">
                                        Cancel
                                    </Link>
                                </form>
                            </div>
                        </div>
                    </div>
                )}
        </div>
    )
}
