package org.t226.studentservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.t226.studentservice.controller.model.DisciplineDto;
import org.t226.studentservice.controller.model.UserEditDto;
import org.t226.studentservice.exception.AppException;
import org.t226.studentservice.exception.ExceptionType;
import org.t226.studentservice.persistance.model.Student;
import org.t226.studentservice.persistance.model.domain.Grade;
import org.t226.studentservice.persistance.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    public static final String STUDENT_NOT_FOUND = "Cannot find the student with the uuid: ";
    private final StudentRepository studentRepository;

    @Override
    public Student save(Student student) {

        return studentRepository.save(student);
    }

    @Override
    public Student find(String uuid) {
        return studentRepository.findStudentByUuid(uuid)
                .orElseThrow(() -> new AppException(STUDENT_NOT_FOUND + uuid, ExceptionType.USERNAME_NOT_FOUND));
    }

    @Override
    public void delete(String uuid) {
        studentRepository.delete(find(uuid));
    }

    @Override
    @Transactional
    public Student update(String uuid, Student updatedStudent) {
        Student student = find(uuid);
        student.setData(updatedStudent.getData());
        student.setValid(updatedStudent.isValid());
        return save(student);
    }

    @Override
    public List<Grade> getGrades(String uuid) {
        return find(uuid).getData().getGrades();
    }

    @Override
    public Student updateFromUserEndpoint(String uuid, UserEditDto userEditDto) {
        Student student = find(uuid);
        String[] splitName = userEditDto.getName().split(" ");
        student.getData().setFirstname(splitName[0]);
        student.getData().setLastname(splitName[1]);
        return save(student);
    }


    @Override
    public float getAverage(String uuid) {
        List<Grade> grades = getGrades(uuid);
        Map<String, List<Integer>> discipline = getDisciplinesMapped(grades);
        float finalGrade = 0.0f;
        for (String dis : discipline.keySet()) {
            float disciplineGrade = round(discipline.get(dis).stream().mapToInt(Integer::intValue).sum() / ((float) discipline.get(dis).size()));
            finalGrade += disciplineGrade;
        }
        return finalGrade / discipline.size();
    }

    @Override
    @Transactional
    public void addMarks(String uuid, Grade grade) {
        Student student = find(uuid);
        if (student.getData().getGrades() == null) {
            student.getData().setGrades(new ArrayList<>());
        }
        List<Grade> newGr = student.getData().getGrades();
        newGr.add(grade);
        student.getData().setGrades(newGr);
        List<Grade> grades = getGrades(uuid);
        boolean isValid = true;
        Map<String, List<Integer>> discipline = getDisciplinesMapped(grades);
        for (String dis : discipline.keySet()) {
            float disciplineGrade = round(discipline.get(dis).stream().mapToInt(Integer::intValue).sum() / ((float) discipline.get(dis).size()));
            if (disciplineGrade < 5.0f) {
                isValid = false;
            }
        }
        student.setValid(isValid);
        save(student);
    }
    @Override
    public float getAverageDiscipline(String uuid, DisciplineDto dis) {
        List<Grade> grades = getGrades(uuid);
        if(grades == null)
            return 0.0f;
        String discipline= dis.getDiscipline();
        Map<String, List<Integer>> disciplines = getDisciplinesMapped(grades);
        if (disciplines.get(discipline) != null)
            return disciplines.get(discipline).stream().mapToInt(Integer::intValue).sum() / ((float) disciplines.get(discipline).size());
        else
            return 0.0f;
    }

    @Transactional
    @Override
    public void deleteGrade(String uuid, Grade grade) {
        List<Grade> grades = getGrades(uuid);
        for (Grade g : grades) {
            if (g.equals(grade)) {
                grades.remove(g);
                break;
            }
        }
        Student student = find(uuid);
        student.getData().setGrades(grades);
        save(student);
    }

    public Map<String, List<Integer>> getDisciplinesMapped(List<Grade> grades) {
        Map<String, List<Integer>> discipline = new HashMap<>();
        for (Grade g : grades) {
            if (discipline.containsKey(g.getDiscipline())) {
                discipline.get(g.getDiscipline()).add(g.getGrade());
            } else {
                discipline.put(g.getDiscipline(), new ArrayList<>());
                discipline.get(g.getDiscipline()).add(g.getGrade());
            }
        }
        return discipline;
    }
}
