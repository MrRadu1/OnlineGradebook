import React from "react";
import {Link, useNavigate} from "react-router-dom";
import "./StyledComponents.css"
export default function Navbar() {
  let navigate = useNavigate();
  let token = sessionStorage.getItem("userGuid");

  function isLoggedIn() {
    if(token != null){
      return(
          <button id="logoutButton" type="submit" className="btn btn-outline-light header-button" onClick={onSubmit}>
            Logout
          </button>
      )
    }
  }

  const onSubmit = async e => {
    sessionStorage.clear();
    navigate('/login');
  }
  return (
    <div>
      <nav className="navbar-expand-lg navbar-dark bg-primary header">
        <div className="container-fluid">
          <Link className="navbar-brand catalogName" to="/">
            Catalog
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <Link className="btn btn-outline-light header-button" to="/register">
            Register
          </Link>
          <Link className="btn btn-outline-light header-button" to="/login">
            Login
          </Link>
            {isLoggedIn()}
        </div>
      </nav>
    </div>
  );
}
