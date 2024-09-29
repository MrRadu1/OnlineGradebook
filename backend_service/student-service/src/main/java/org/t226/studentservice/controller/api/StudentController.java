package org.t226.studentservice.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.t226.studentservice.controller.model.DisciplineDto;
import org.t226.studentservice.controller.model.ExternalStudent;
import org.t226.studentservice.controller.model.Grade;
import org.t226.studentservice.controller.model.UserEditDto;
import org.t226.studentservice.facade.StudentFacade;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private static final String DELETED = "The Student has been deleted";

    private static final String ADDED = "The Grade has been added";

    public final StudentFacade studentFacade;

    @PostMapping
    public ResponseEntity<ExternalStudent> save(@RequestBody ExternalStudent externalStudent) {
        return new ResponseEntity<>(studentFacade.save(externalStudent), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ExternalStudent> find(@PathVariable String uuid) {
        return new ResponseEntity<>(studentFacade.find(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> delete(@PathVariable String uuid) {
        studentFacade.delete(uuid);
        return new ResponseEntity<>(DELETED, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-from-user/{uuid}")
    public ResponseEntity<String> deleteFromUser(@PathVariable String uuid) {
        studentFacade.deleteFromUser(uuid);
        return new ResponseEntity<>(DELETED, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ExternalStudent> update(@PathVariable String uuid, @RequestBody ExternalStudent student) {
        return new ResponseEntity<>(studentFacade.update(uuid, student), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/grades")
    public ResponseEntity<List<Grade>> findGrades(@PathVariable String uuid) {
        return new ResponseEntity<>(studentFacade.findGrades(uuid), HttpStatus.OK);
    }

    @PutMapping("/edit-from-user/{uuid}")
    public ResponseEntity<ExternalStudent> updateFromUserEndpoint(@PathVariable String uuid, @RequestBody UserEditDto student) {
        return new ResponseEntity<>(studentFacade.updateFromUserEndpoint(uuid, student), HttpStatus.OK);
    }
    @GetMapping("/{uuid}/average")
    public ResponseEntity<Float> getAverage(@PathVariable String uuid) {
        return new ResponseEntity<>(studentFacade.getAverage(uuid), HttpStatus.OK);
    }

    @PostMapping("/{uuid}/disciplineAverage")
    public ResponseEntity<Float> getDisciplineAverage(@PathVariable String uuid,  @RequestBody DisciplineDto discipline) {
        return new ResponseEntity<>(studentFacade.getDisciplineAverage(uuid,discipline), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/addMarks")
    public ResponseEntity<String> addMarks(@PathVariable String uuid, @RequestBody org.t226.studentservice.persistance.model.domain.Grade grade) {
        studentFacade.addMarks(uuid, grade);
        return new ResponseEntity<>(ADDED, HttpStatus.OK);
    }

    @PostMapping("/{uuid}/deleteGrade")
    public void deleteGrade(@PathVariable String uuid, @RequestBody org.t226.studentservice.persistance.model.domain.Grade grade){
        studentFacade.deleteGrade(uuid, grade);
    }

}
