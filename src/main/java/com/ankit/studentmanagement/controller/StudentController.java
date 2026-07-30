package com.ankit.studentmanagement.controller;

import com.ankit.studentmanagement.dto.request.StudentRequest;
import com.ankit.studentmanagement.dto.response.StudentResponse;
import com.ankit.studentmanagement.entity.Student;
import com.ankit.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
@Tag(
        name = "Student Management API",
        description = "Rest API for managing Students"
)
@RestController
@RequestMapping("/api/students")
public class StudentController {


    // This is basic not good for real life projects
//    private StudentRepository repository;
//
//    public StudentController(StudentRepository repository) {
//        this.repository = repository;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Student>> getAll() {
//        return ResponseEntity.ok(repository.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Student> getById(@PathVariable Integer id) {
//        Student student = repository.findById(id)
//                .orElseThrow(() -> new StudentNotFoundException(id));
//     Optional<Student> student = repository.findById(id);
//       if(student.isPresent()){
//           Student s = (Student) student.get();
//           return ResponseEntity.ok(s);
//       }else{
//           return ResponseEntity.notFound().build();
//       }

//       we can also write
//        1) Student student = repository.findById(id)
//                .orElse(defaultStudent);
//        2)Student student = repository.findById(id)
//                .orElseGet(() -> createDefaultStudent());
        //most commonly used
//        return ResponseEntity.ok(student);
//    }
//
//
//    @PostMapping
//    public ResponseEntity createStudent(@RequestBody StudentRequest request){
//       return  ResponseEntity.ok(repository.save(request));
//    }


    private final StudentService studentService;


    public StudentController(StudentService Studentservice) {
        this.studentService = Studentservice;
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<StudentResponse> getStudentById(@PathVariable Integer id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudents(@PathVariable Integer id , @Valid @RequestBody StudentRequest request){
        return ResponseEntity.ok(studentService.updateStudents(id, request));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<StudentResponse> deleteStudent (@PathVariable Integer id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
   @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> searchStudentByName(@RequestParam String name){
        return ResponseEntity.ok(studentService.searchStudentsByName(name));
   }
}
