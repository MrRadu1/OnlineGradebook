import "./App.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from "./layout/Navbar";
import Home from "./pages/Home";

import Register from "./pages/Register";
import EditUser from "./users/EditUser";
import ViewUser from "./users/ViewUser";
import Login from "./pages/Login";
import {useEffect, useState} from "react";
import {Route, Routes, useNavigate} from "react-router-dom";
import {createContext, useContext} from "react";
import AddMarks from "./users/AddMarks";
import ViewMarks from "./users/ViewMarks";
const Context = createContext(undefined);
function App() {
  let navigate = useNavigate();
  const [tokenId, setToken] = useState();
  const token = sessionStorage.getItem("userGuid");
  console.log("token = ",token);
  useEffect(() => {
    if(token == null){
     navigate('/login');
    }
  }, [token])
  return (
    <div className="App">
      <Navbar />
        <Routes>
          <Route exact path="/login" element ={<Login />} />
          <Route exact path="/" element={<Home />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/edituser/:id" element={<EditUser />} />
          <Route exact path="/viewuser/:id" element={<ViewUser />} />
          <Route exact path="/addmarks/:id" element={<AddMarks />} />
          <Route exact path="/viewmarks/:id" element={<ViewMarks />} />
        </Routes>

    </div>
  );
}

export default App;
