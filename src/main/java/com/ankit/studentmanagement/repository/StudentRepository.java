package com.ankit.studentmanagement.repository;

import com.ankit.studentmanagement.dto.response.StudentResponse;
import com.ankit.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM  Student s WHERE s.age > :age")
    List<Student> findStudentsOlderThan(@Param("age") Integer age);

    @Query(
    value = "SELECT * FROM STUDENT WHERE  age > :age",
            nativeQuery = true
    )
    List<Student> findStudentsOlderThanNative(@Param("age") Integer age);

    Optional<Student> findStudentByEmail(String email);


}