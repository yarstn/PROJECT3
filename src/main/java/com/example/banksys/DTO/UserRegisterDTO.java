package com.example.banksys.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 5, message = "Username must be longer than 4 characters")
    private String username;

//    @NotEmpty(message = "Password cannot be null or empty")
//    @Size(min = 6, message = "Password must be longer than 5 characters")
    private String password;

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 3, message = "Name must be longer than 2 characters")
    private String name;

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    private String email;

    @NotEmpty(message = "Role cannot be null or empty")
    private String role;
}
