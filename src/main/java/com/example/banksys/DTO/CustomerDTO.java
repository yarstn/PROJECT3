package com.example.banksys.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends UserRegisterDTO {

    @NotEmpty(message = "Phone number cannot be null or empty")
    @Pattern(regexp = "^(\\+9665|05|5)([0-9]{8})$", message = "Invalid phone number format")
    private String phoneNumber;

    public CustomerDTO() {
        this.setRole("CUSTOMER");
    }
}
