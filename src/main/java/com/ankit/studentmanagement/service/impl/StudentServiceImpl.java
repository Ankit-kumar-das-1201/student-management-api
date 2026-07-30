package com.ankit.studentmanagement.service.impl;

import com.ankit.studentmanagement.dto.request.StudentRequest;
import com.ankit.studentmanagement.dto.response.StudentResponse;
import com.ankit.studentmanagement.entity.Student;
import com.ankit.studentmanagement.exception.StudentNotFoundException;
import com.ankit.studentmanagement.repository.StudentRepository;
import com.ankit.studentmanagement.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getAge(),
                student.getName(),
                student.getEmail()
        );

    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
       Page<Student> studentPage = studentRepository.findAll(pageable);
       return studentPage.map(this::mapToResponse);

    }

    @Override
    public StudentResponse getStudentById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return mapToResponse(student);
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        student.setAge(request.age());

        Student savedStudent = studentRepository.save(student);
//        StudentResponse response = new StudentResponse(savedStudent.getId(),savedStudent.getAge(), savedStudent.getName(), savedStudent.getEmail());
//        return response;
        return mapToResponse(savedStudent);
    }

    @Override
    public StudentResponse updateStudents(Integer id, StudentRequest request) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        student.setName(request.name());
        student.setAge(request.age());
        student.setEmail(request.email());
      studentRepository.save(student);
      return mapToResponse(student);
    }

    @Override
    public void deleteStudent(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);

    }
    @Override
    public List<StudentResponse> searchStudentsByName(String name) {

        List<Student> students = studentRepository.findByNameContainingIgnoreCase(name);

        return students.stream()
                .map(this::mapToResponse)
                .toList();
    }
}
