import React, {Component, useContext, useState} from 'react';
import './StyledComponents.css';
import { Form , Input, Button, Checkbox, message } from "antd";


import loginImg from "../resource/login.png"
import {useNavigate} from "react-router-dom";
import {Context} from "../App.js"
const FormItem = Form.Item;

export default function Login(){
    //const [token,setToken] = useContext(Context);
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [error , setError] = useState();
    const [loading, setLoading] = useState(false);
    //console.log(error);
    //console.log(loading);

    let navigate = useNavigate();
    const [userLogin, setUser] = useState({
        username: "",
        password: "",
    });

    async function loginUser(credentials) {
        setLoading(true);
        const result = await fetch('http://localhost:8080/auth/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        });
        let data = await result.json();
        setLoading(false);
        if(result.ok){
            sessionStorage.setItem("userInfo", JSON.stringify(data));
            sessionStorage.setItem("userGuid", data.uuid);
            // sessionStorage.setItem("username", data.username);
            // sessionStorage.setItem("userRole", data.permission);
            navigate('/');
        }
        else{
            setError('Invalid login!');
        }
    }


    const onInputChange = (e) => {
        setUser({ ...userLogin, [e.target.name]: e.target.value });
    };


    const handleSubmit = async e => {
        e.preventDefault();
        await loginUser(userLogin);
    }


    return (
        <div>
            {loading ?(
                <div className="loader-container">
                    <div className="spinner"></div>
                </div>
            ):(
            <div className="lItem">

                <div className="loginImage">
                    <img src={loginImg} width="300" style={{position: 'relative'}} alt="login"/>
                </div>
                <div className="loginForm">
                    <h2>Login</h2>
                    {error && <p className="styledError">{error}</p>}
                    <form  className="login-form">

                        <FormItem   rules={[{ required: true,  message: "Please enter your username" }]}>

                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter your username"
                                name="username"
                                value={username}
                                onChange={(e) => onInputChange(e)}
                            />

                        </FormItem>
                        <FormItem name="password" rules={[{ required: true,  message: "Please enter your password" }]}>
                            <br></br>
                            <input
                                type={"password"}
                                className="form-control"
                                placeholder="Enter your password"
                                name="password"
                                value={password}
                                onChange={(e) => onInputChange(e)}
                            />
                        </FormItem>
                        <FormItem >
                            <Checkbox className={"check-box"}>Remember me</Checkbox>
                            <button
                                type="submit"
                                className="login-button"
                                 onClick={handleSubmit}
                            >
                                Log in
                            </button>
                            <br></br>
                            <br></br>
                            <br></br>
                        </FormItem>
                    </form>
                </div>
                <div className="footer">
                    <a href="" target="_blank" rel="noopener noreferrer" className="footerLink">Made by Baietii de la 226</a>
                </div>
            </div>
            )}
        </div>
    );
}


