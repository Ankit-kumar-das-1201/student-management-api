package com.ankit.studentmanagement.exception;

public class StudentNotFoundWithEmail extends RuntimeException{
    public StudentNotFoundWithEmail(String email){
        super("Student with email : " + email + " is not Found");
    }
}
