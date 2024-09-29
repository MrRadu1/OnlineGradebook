import axios from "axios";
import React, {useDebugValue, useEffect, useReducer, useState} from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import './StyledComponents.css';


export default function ViewMarks() {
    let navigate = useNavigate();

    const { id } = useParams();

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

    function showGrade(){
        if (gradeModel.grades !== null) {
            return (
                <div>
                    <table border="1" id="customers">
                        <tbody>
                        <tr>
                            <th>Class Name</th>
                            <th>Grade</th>
                            <th>Date</th>
                        </tr>
                        {gradeModel.grades.filter(item => item.discipline.toString() === disciplineName.disciplineName.toString()).map((item, i) => (
                            <tr key={i}>
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
    const onInputChange = (e) => {
        setGradeModel({ ...gradeModel, [e.target.name]: e.target.value });
    };

    const onInputChangeDiscipline = (e) => {
        setDisciplineName({ ...disciplineName, [e.target.name]: e.target.value });
    };

    useEffect(() => {
        loadGrades();
    }, [disciplineName,gradeModel.grades]);

    const onSubmit = async (e) => {
        e.preventDefault();
    };

    const loadGrades = async () => {
        const result = await axios.get(`http://localhost:8081/api/students/${id}`);
        console.log(result.data);
        setGradeModel(result.data);
    };


    const[semesterAverage, setSemesterAverage] = useState(null);

    const getAverageValue = async () => {
        setSomethingSelectedSemester(true)
        const result = await axios.get(`http://localhost:8081/api/students/${id}/average`);
        setSemesterAverage(result.data);
    };

    function showSemesterAverage(){
        if (semesterAverage !== null && somethingSelectedSemester === true) {
            return (
                <div className="semesterAverage">
                    <h5> Semester average: <b>{semesterAverage}</b> </h5>
                </div>
            )
        }
    }

    const[classAverage, setClassAverage] = useState(0);

    const getClassAverage = async () => {
        setSomethingSelected(true)
        const result = await fetch(`http://localhost:8081/api/students/${id}/disciplineAverage`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({discipline: disciplineName.disciplineName})
        });
        let resultData = await result.json();
        setClassAverage(resultData);
        console.log(classAverage);
    };


    function showClassAverage(){
        if (classAverage !== 0 && somethingSelected === true) {
            return (
                <div className="semesterAverage ">
                    <h5>  {disciplineName.disciplineName} average: <b>{classAverage}</b> </h5>
                </div>
            )
        }
    }


    const[somethingSelected, setSomethingSelected] = useState(false);

    function setFalseSomethingSelected(){
        setSomethingSelected(false)
        setSomethingSelectedSemester(false)
    }

    const[somethingSelectedSemester, setSomethingSelectedSemester] = useState(false);

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

    function showAverageButtons(){
        if(gradeModel.grades !== null) {
            return (
                <div>
                    <button type="submit" className="btn btn-outline-primary" onClick={getClassAverage}>
                        Calculate Average for Class
                    </button>
                    <button type="submit" className="btn btn-outline-primary avgSemester" onClick={getAverageValue}>
                        Calculate Average for Semester
                    </button>
                </div>
            )
        }else{
            return(
                <div></div>
            )
        }
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className="text-center m-4">View Marks for {gradeModel.firstname}</h2>
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
                                onClick={setFalseSomethingSelected}
                            />
                            {showClasses()}
                        </div>
                        <div className="mb-3">
                            {showGrade()}
                        </div>
                        {showAverageButtons()}
                        <Link className="btn btn-outline-danger mx-2 cancelButtonDown" to="/">
                            Cancel
                        </Link>
                        {showClassAverage()}
                        {showSemesterAverage()}
                    </form>
                </div>
            </div>
        </div>
    );
}
