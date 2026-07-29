package com.ankit.studentmanagement.dto.request;

import jakarta.validation.constraints.*;

public record StudentRequest(

        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Must be a Valid Format")
        String email,

        @NotNull(message = "Age must be required")
        @Min(value = 18, message = "Age must atleast be  18")
        @Max(value= 100, message = "Age must not exceeds 100")
        Integer age
) {
}
