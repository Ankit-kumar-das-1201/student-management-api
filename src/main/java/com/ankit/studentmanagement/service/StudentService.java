package com.ankit.studentmanagement.service;

import com.ankit.studentmanagement.dto.request.StudentRequest;
import com.ankit.studentmanagement.dto.response.StudentResponse;
import com.ankit.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

//    List<StudentResponse> getAllStudents();

    Page<StudentResponse> getAllStudents(Pageable pageable);
    StudentResponse getStudentById(Integer id);

    StudentResponse createStudent(StudentRequest request);

    StudentResponse updateStudents(Integer id, StudentRequest request);

    void deleteStudent(Integer id);

    List<StudentResponse> searchStudentsByName(String name);

    public StudentResponse updateStudentName(Integer id);

    public List<StudentResponse> findStudentsOlderThan(Integer age);

    public  List<StudentResponse> findStudentsOlderThanNative(Integer age);
}


