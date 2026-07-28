package com.ankit.studentmanagement.dto.request;

public record StudentRequest(
        String name,
        String email,
        Integer age
) {
}
