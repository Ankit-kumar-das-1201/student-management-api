package com.ankit.studentmanagement.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Integer id) {
        System.out.println("Student with id : " + id + " is not Found");
    }
}
