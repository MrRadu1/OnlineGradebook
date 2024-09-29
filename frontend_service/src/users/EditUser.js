import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ToastMessenger from "../resource/ToastMessenger";
import {ToastContainer} from "react-toastify";

export default function EditUser() {
  let navigate = useNavigate();
  const { id } = useParams();
  const [user, setUser] = useState({
    name: "",
    username: "",
    email: ""
  });

  const [isError, setIsError] = useState(false);

  const [error, setError] = useState({
    message:"",
    timestamp:"",
    httpRequest:"",
    exceptionType:""
  });

  const { name, username, email } = user;

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  useEffect(() => {
    loadUser();
  }, []);

  const onSubmit = async (e) => {
    e.preventDefault();
    const result = await fetch(`http://localhost:8080/api/users/${id}`, {
      method: "PUT",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    });
    if(result.ok){
      navigate("/");
    }else{
      let data = await result.json();
      setError(data);
      setIsError(true);
      ToastMessenger(data.message, "error");
    }
  };

  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/api/users/${id}`);
    setUser(result.data);
  };

  const fieldColor = (name) =>{
    return isError && (name === error.exceptionType) ? "form-control editUserErrorInput": "form-control";
  }
  const resetFieldColor = () =>{
    setIsError(false);
  }

  return (
    <div className="container">
      <ToastContainer />
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Edit User</h2>

          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Name" className="form-label">
                Name
              </label>
              <input
                type={"text"}
                className="form-control"
                placeholder="Enter your name"
                name="name"
                value={name}
                onChange={(e) => onInputChange(e)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Username" className="form-label">
                Username
              </label>
              <input
                type={"text"}
                className={fieldColor("USERNAME_ALREADY_EXISTS")}
                placeholder="Enter your username"
                name="username"
                value={username}
                onChange={(e) => onInputChange(e)}
                onClick={resetFieldColor}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="Email" className="form-label">
                E-mail
              </label>
              <input
                type={"text"}
                className={fieldColor("EMAIL_ALREADY_EXISTS")}
                placeholder="Enter your e-mail address"
                name="email"
                value={email}
                onChange={(e) => onInputChange(e)}
                onClick={resetFieldColor}
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
  );
}
