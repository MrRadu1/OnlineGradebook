package org.t226.studentservice.service;


import org.t226.studentservice.controller.model.DisciplineDto;
import org.t226.studentservice.controller.model.UserEditDto;
import org.t226.studentservice.persistance.model.Student;
import org.t226.studentservice.persistance.model.domain.Grade;

import java.util.List;

public interface StudentService {
    Student save(Student student);

    Student find(String uuid);

    void delete(String uuid);

    Student update(String uuid, Student updatedStudent);

    List<Grade> getGrades(String uuid);

    Student updateFromUserEndpoint(String uuid, UserEditDto userEditDto);

    float getAverage(String uuid);

    void addMarks(String uuid, Grade grade);

    float getAverageDiscipline(String uuid, DisciplineDto dis);

    void deleteGrade(String uuid, Grade grade);
}
