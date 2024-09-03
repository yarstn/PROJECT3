package com.example.banksys.Controller;

import com.example.banksys.DTO.CustomerDTO;
import com.example.banksys.Model.Customer;
import com.example.banksys.Model.User;
import com.example.banksys.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getAllCustomers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @PostMapping("/register/customer")
    public ResponseEntity registerCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return new ResponseEntity("Customer registered successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User customerId, @Validated @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerId.getId(), customerDTO);
        return new ResponseEntity("Customer updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user, @PathVariable Integer customerId) {
        customerService.deleteCustomer(user.getId(), customerId);
        return new ResponseEntity("Customer deleted successfully", HttpStatus.OK);
    }
    // Endpoint to get the details of the authenticated customer
    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails(@AuthenticationPrincipal User user) {
        Customer customer = customerService.getCustomerDetails(user.getId());
        return ResponseEntity.ok(customer);
    }
}
