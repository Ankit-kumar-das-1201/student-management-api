package com.ankit.studentmanagement.dto.response;

public record StudentResponse (
    Integer id,
    Integer age,
    String name,
    String email
){
}
