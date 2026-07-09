package com.Shivraj.resume_screening_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Name must be required")
    private String name;

    @NotBlank(message="email must me required")
    @Email(message ="Enter the valid email")
    private String email;

    @NotBlank(message ="password must be required")
    @Size(min= 8, max=20, message="password must be between 8 and 20 characters")
    private String password;
}
