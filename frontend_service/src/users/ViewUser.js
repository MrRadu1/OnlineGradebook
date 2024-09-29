import axios from "axios";
import React, { useEffect,useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewUser() {
  const [user, setUser] = useState({
    name: "",
    username: "",
    email: "",
    address:{
      street:"",
      number:"",
      city:"",
      country:""
    }
  });
  let currentUser = JSON.parse(sessionStorage.getItem("userInfo"));
  const { id } = useParams();

  useEffect(() => {
    loadUser();
  }, []);
  const loadUser = async () => {
    const result = await axios.get(`http://localhost:8080/api/users/${id}`);
    setUser(result.data);
  };

  function displayMessage(){
    if(currentUser.role === "ADMIN"){
      return(
          <p> Details of user with id : {id}</p>
      )
    }else{
      return(
          <p>You can find all your details here.</p>
      )
    }
  }
  function showAddress(){
    let address = user.address.country +
        ", " +
        user.address.city +
        ", "+
        user.address.street +
        " nr." +
        user.address.number;
    return (
        <p> {address} </p>
    );
  }
  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">User Details</h2>

          <div className="card">
            <div className="card-header">
              {displayMessage()}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Name: </b>
                  {user.name}
                </li>
                <li className="list-group-item">
                  <b>Username: </b>
                  {user.username}
                </li>
                <li className="list-group-item">
                  <b>Email: </b>
                  {user.email}
                </li>
              <li className="list-group-item">
                <b>Address: </b>
                {showAddress()}
              </li>
              </ul>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Home
          </Link>
        </div>
      </div>
    </div>
  );
}
