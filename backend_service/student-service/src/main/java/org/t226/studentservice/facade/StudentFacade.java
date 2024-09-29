package org.t226.studentservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.t226.studentservice.controller.model.DisciplineDto;
import org.t226.studentservice.controller.model.ExternalStudent;
import org.t226.studentservice.controller.model.UserEditDto;
import org.t226.studentservice.facade.converters.GradeConverter;
import org.t226.studentservice.facade.converters.StudentConverter;
import org.t226.studentservice.facade.converters.UserEditConverter;
import org.t226.studentservice.facade.model.UserDto;
import org.t226.studentservice.persistance.model.Student;
import org.t226.studentservice.service.StudentService;
import org.t226.studentservice.controller.model.Grade;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class StudentFacade {

    private final StudentService studentService;
    private final StudentConverter converter;
    private final GradeConverter gradeConverter;
    private final UserEditConverter userEditConverter;

    public ExternalStudent save(ExternalStudent student) {
        Student returnedStudent = studentService.save(converter.toModel(student));
        return converter.toDto(returnedStudent);
    }

    public ExternalStudent find(String uuid) {
        return converter.toDto(studentService.find(uuid));
    }

    public void delete(String uuid) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/api/users/" + uuid);
        studentService.delete(uuid);
    }

    public void deleteFromUser(String uuid) {
        studentService.delete(uuid);
    }

    public ExternalStudent update(String uuid, ExternalStudent student) {
        Student returnedStudent = studentService.update(uuid, converter.toModel(student));
        RestTemplate restTemplate = new RestTemplate();
        UserDto user = restTemplate.getForEntity("http://localhost:8081/api/users/" + uuid, UserDto.class).getBody();
        Objects.requireNonNull(user).setName(student.getFirstname() + student.getLastname());
        restTemplate.put("http://localhost:8081/api/users/" + uuid, user, UserDto.class);
        return converter.toDto(returnedStudent);
    }

    public List<Grade> findGrades(String uuid) {
        List<org.t226.studentservice.persistance.model.domain.Grade> grades = studentService.getGrades(uuid);
        return grades.stream().map(gradeConverter::toDto).collect(Collectors.toList());
    }

    public float getAverage(String uuid) {
        return studentService.getAverage(uuid);
    }

    public void addMarks(String uuid, org.t226.studentservice.persistance.model.domain.Grade grade) {
        studentService.addMarks(uuid, grade);
    }

    public float getDisciplineAverage(String uuid, DisciplineDto discipline) {
        return studentService.getAverageDiscipline(uuid,discipline);
    }

    public ExternalStudent updateFromUserEndpoint(String uuid, UserEditDto student) {
        return converter.toDto(studentService.updateFromUserEndpoint(uuid, student));
    }

    public void deleteGrade(String uuid, org.t226.studentservice.persistance.model.domain.Grade grade) {
        studentService.deleteGrade(uuid,grade);
    }
}
