package com.example.banksys.Repository;

import com.example.banksys.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeeRepository  extends JpaRepository<Employee, Integer> {
    Employee findEmployeeById(Integer id);
}
