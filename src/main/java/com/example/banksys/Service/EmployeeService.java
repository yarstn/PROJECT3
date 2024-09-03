package com.example.banksys.Service;

import com.example.banksys.Api.ApiException;
import com.example.banksys.DTO.EmployeeRegistrationDTO;
import com.example.banksys.Model.Employee;
import com.example.banksys.Model.User;
import com.example.banksys.Repository.EmployeeRepository;
import com.example.banksys.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//GET
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    //ADD
    public void registerEmployee(EmployeeRegistrationDTO employeeDTO) {
        User user = new User();
        user.setUsername(employeeDTO.getUsername());
        user.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole(employeeDTO.getRole());

        Employee employee = new Employee();
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employee.setUser(user);

        user.setEmployee(employee);

        userRepository.save(user);
    }
   //UPDATE
   public void updateEmployee(Integer employeeId, EmployeeRegistrationDTO employeeDTO) {
       Employee existingEmployee = employeeRepository.findEmployeeById(employeeId);
       if (existingEmployee == null) {
           throw new ApiException("employee not found");
       }

       User existingUser = existingEmployee.getUser();
      existingUser.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
       existingUser.setName(employeeDTO.getName());
       existingUser.setEmail(employeeDTO.getEmail());
       // Update Employee fields
       existingEmployee.setPosition(employeeDTO.getPosition());
       existingEmployee.setSalary(employeeDTO.getSalary());
       // Save the updated User and Employee
       userRepository.save(existingUser);
       employeeRepository.save(existingEmployee);
   }
   public void deleteEmployee(Integer id,Integer employeeId) {
        User user = userRepository.findUserById(id);
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            throw new ApiException("employee not found");
        }
        if (user.getEmployee().getId() != employee.getId()) {
            throw new ApiException("employee id mismatch");
        }
        employeeRepository.delete(employee);
   }

}
