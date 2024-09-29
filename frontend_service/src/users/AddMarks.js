import axios from "axios";
import React, {useDebugValue, useEffect, useReducer, useRef, useState} from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import './StyledComponents.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ToastMessenger from "../resource/ToastMessenger";
export default function AddMarks() {
    let navigate = useNavigate();
    const [error, setError] = useState();
    const { id } = useParams();
    const [loading, setLoading] = useState(false);
    const [gradeModel, setGradeModel] = useState({
        firstname: "",
        lastname: "",
        studentId: "",
        grades: [{
            discipline:"",
            date:"",
            grade:""
        }]
    });
    const [disciplineName, setDisciplineName] = useState({
        disciplineName:""
    })

    const [inputFields, setInputFields] = useState([
        {
            discipline: '',
            date: '',
            grade: ''
        }
    ])



    const [selectedRowTable, setSelectedRowTable] = useState({
        selected:false,
        index: 0,
        hovering:false
    })

    const [gradeToDelete, setGradeToDelete] = useState(
        {
            discipline: '',
            grade: '',
            date: ''
        }
    )

    function unique(a, fn) {
        if (a.length === 0 || a.length === 1) {
            return a;
        }
        if (!fn) {
            return a;
        }

        for (let i = 0; i < a.length; i++) {
            for (let j = i + 1; j < a.length; j++) {
                if (fn(a[i], a[j])) {
                    a.splice(i, 1);
                }
            }
        }
        return a;
    }

    function showClasses(){

        if(gradeModel.grades === null){

            return(
            <datalist id="ClassList">
                <option value="Matematica">Matematica</option>
                <option value="Limba Romana">Limba Romana</option>
                <option value="Limba Engleza">Limba Engleza</option>
                <option value="Fizica">Fizica</option>
                <option value="Chimie">Chimie</option>
                <option value="Infromatica">Informatica</option>
                <option value="Geografie">Geografie</option>
            </datalist>
            )
        }else {
            const gradeNames = []
            gradeModel.grades.map((item, i) => {
                gradeNames.push(item.discipline);
            })
            let studentClasses = [...new Set(gradeNames)]
            console.log("studentClasses", studentClasses);
            return (
                <datalist id="ClassList">
                    {studentClasses.map((item, i) => (
                        <option value={item}>{item}</option>
                    ))}
                </datalist>
            )
        }
    }

    const onInputChange = (e) => {
        setGradeModel({ ...gradeModel, [e.target.name]: e.target.value });
    };

    const onInputChangeDiscipline = (e) => {
        setDisciplineName({ ...disciplineName, [e.target.name]: e.target.value });
    };

    const [refreshOnAction, setRefreshOnAction] = useState();

    useEffect(() => {
        loadGrades();
    }, [disciplineName,refreshOnAction]);

    const onSubmit = async (e) => {
        e.preventDefault();

    };

    const loadGrades = async () => {
        const result = await axios.get(`http://localhost:8081/api/students/${id}`);
        console.log(result.data);
        setGradeModel(result.data);
    };

    const handleFormChange = (index, event) => {
        let data = [...inputFields];
        data[index][event.target.name] = event.target.value;
        setInputFields(data);
    }

    let addMoreFirstPushed = false;
    const addFields = () => {
        let newfield =
            {
                discipline: '',
                grade: '',
                date: ''
            }
        addMoreFirstPushed = true;
        setInputFields([...inputFields, newfield])
    }

    const removeAllFields = (dataLength) =>{
        setLoading(true);
        let data = [...inputFields];
        data.splice(1, dataLength);
        setInputFields(data);
        setLoading(false);
    }

    const removeFields = (index) => {
        setLoading(true);
        let data = [...inputFields];
        data.splice(index, 1);
        setInputFields(data);
        setRefreshOnAction(index);
        setLoading(false);
    }

    async function registerGrade(gradeData, gradeFieldsNumber){
        setLoading(true);
        if(gradeData.date !== '') {
            var [day, month, year] = gradeData.date.split('-');
            gradeData.date = year + "-" + month + "-" + day;
        }else{
            var dateNow = new Date();
            gradeData.date = dateNow.getFullYear() + "-" + (dateNow.getMonth()+1) + "-" + dateNow.getDate();
        }
        const result = await fetch(`http://localhost:8081/api/students/${id}/addMarks`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(gradeData)
        });
        setLoading(false);
        if (result.ok) {
            setRefreshOnAction(0);
            ToastMessenger("Grade registered successfully!", "success");
            removeAllFields(gradeFieldsNumber);
        } else {
            setError('Cannot register grade!');
            ToastMessenger("Cannot register grade!", "error");
        }
    }

    const formSubmit = async (e) => {
        e.preventDefault();
        let gradeData = [...inputFields];
        let length = gradeData.length;
        for (var i = 1; i < gradeData.length; i++) {
            await registerGrade(gradeData[i], length);
        }
    }

    function onClickGrade(discipline, grade, date, index){
        let gradeToBeDeleted =
            {
                discipline: discipline,
                grade: grade,
                date: date
            };
        let selectedRow =
            {
                selected:!selectedRowTable.selected,
                index: index,
                hovering: false
            };

        console.log("index of row= ",index);
        console.log("gradeToBe Deleted =  ", gradeToBeDeleted);
        setGradeToDelete(gradeToBeDeleted);
        console.log("selectedRo2 = ", selectedRowTable);
        setSelectedRowTable(selectedRow);
    }

    async function deleteGrade() {
        let discipline = disciplineName.disciplineName;
        setLoading(true);
        const result = await fetch(`http://localhost:8081/api/students/${id}/deleteGrade`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(gradeToDelete)
        });
        setRefreshOnAction(1);
        setDisciplineName({ ...disciplineName, disciplineName: discipline });
        setLoading(false);
        if (result.ok) {
            ToastMessenger("Grade deleted successfully!","success");
        } else {
            setError('Cannot delete grade!');
            ToastMessenger("Something went wrong!","error");
        }

    }

    function selectedRow(id) {
            return (selectedRowTable.selected && selectedRowTable.index === id) ? "selectedRow" : "trHover"
    }

    function showGrade(){
        if (gradeModel.grades !== null) {
            return (
                <div>
                    <table border="1" id="customers">
                        <tbody>
                        <tr>
                            <th>Class Name</th>
                            <th>Mark</th>
                            <th>Date</th>
                        </tr>
                        {gradeModel.grades.filter(item => item.discipline.toString() === disciplineName.disciplineName.toString()).map((item, i) => (
                            <tr className={selectedRow(i)}  key={i}  onClick={() => onClickGrade(item.discipline,item.grade,item.date, i)}>
                                <td>{item.discipline}</td>
                                <td>{item.grade}</td>
                                <td>{item.date}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )
        } else {
            return(
                <div>
                    <h4>This student does not have any grades!</h4>
                </div>
            );
        }
    }

    function addForm(){
        return(
            <div>
                    <form>
                        {inputFields.map((input, index) => {
                            if(index !== 0)
                                return (
                                    <div className="addNewGradeDiv">
                                        <div key={index}>
                                            <input
                                                name='discipline'
                                                placeholder='Discipline'
                                                type="search"
                                                value={input.discipline}
                                                onChange={event => handleFormChange(index, event)}
                                            />
                                            <input
                                                name='grade'
                                                placeholder='Grade'
                                                value={input.grade}
                                                onChange={event => handleFormChange(index, event)}
                                            />
                                            <input
                                                name='date'
                                                placeholder='Date'
                                                value={input.date}
                                                onChange={event => handleFormChange(index, event)}
                                            />
                                        </div>
                                        <div className="addNewGradeButtons">
                                        <button onClick={() => removeFields(index)} className="btn btn-danger mx-2">Remove</button>
                                        </div>
                                    </div>
                                )
                        })}
                    </form>
                {error && <p>{error}</p>}
            </div>
        )
    }

    function showDeleteGradeButton() {
        if( selectedRowTable.selected){
            return(
                <button onClick={deleteGrade} className="btn btn-danger mx-2">
                    Remove Selected Mark
                </button>
            )
        }
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
                    <h2 className="text-center m-4">Add Marks for {gradeModel.firstname}</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="Class name" className="form-label">
                                Class Name:
                            </label>
                            <input
                                type={"search"}
                                list = "ClassList"
                                className="form-control"
                                placeholder="Enter the class"
                                name="disciplineName"
                                onChange={(e) => onInputChangeDiscipline(e)}
                            />

                            {showClasses()}

                        </div>
                        <div className="mb-3">
                            {showGrade()}
                        </div>

                        <div>
                            {addForm()}
                        </div>
                        <button type="submit" className="buttonAddGrade btn btn-outline-primary" onClick={addFields}>
                            Add Grade
                        </button>
                        <button onClick={formSubmit} className="btn btn-outline-primary">
                            Submit
                        </button>
                        <Link className="btn btn-outline-danger mx-2" to="/">
                            Cancel
                        </Link>
                        {showDeleteGradeButton()}
                    </form>
                </div>
            </div>
        </div>
        )}
    </div>
    );
}
