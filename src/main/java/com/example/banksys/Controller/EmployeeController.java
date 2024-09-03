package com.example.banksys.Controller;

import com.example.banksys.DTO.EmployeeRegistrationDTO;
import com.example.banksys.Model.User;
import com.example.banksys.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
@RestController //json
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity getAllEmployees(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    // Endpoint to register a new employee
    @PostMapping("/register/employee")
    public ResponseEntity registerEmployee( @Validated @RequestBody EmployeeRegistrationDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return new ResponseEntity("Employee registered successfully", HttpStatus.CREATED);
    }
    //update emp
    @PutMapping("/update")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user, @Validated @RequestBody EmployeeRegistrationDTO employeeDTO) {
        employeeService.updateEmployee(user.getId(), employeeDTO);
        return new ResponseEntity("Employee updated successfully", HttpStatus.CREATED);
    }
    //DELETE EMP BY ADMIN
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user,@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(user.getId(),employeeId);
        return new ResponseEntity("employee deleted ", HttpStatus.CREATED);
    }
}
