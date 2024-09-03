package com.example.banksys.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRegistrationDTO extends UserRegisterDTO {

    @NotEmpty(message = "Position cannot be null or empty")
    private String position;

    @Min(value = 1, message = "Salary must be greater than 0")
    private int salary;

    public EmployeeRegistrationDTO() {
        this.setRole("EMPLOYEE");
    }
}
