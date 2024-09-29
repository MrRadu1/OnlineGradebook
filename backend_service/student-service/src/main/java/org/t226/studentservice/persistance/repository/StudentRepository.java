package org.t226.studentservice.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.t226.studentservice.persistance.model.Student;

import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.uuid = ?1")
    Optional<Student> findStudentByUuid(String uuid);

}
