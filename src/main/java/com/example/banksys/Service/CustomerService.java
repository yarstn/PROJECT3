package com.example.banksys.Service;

import com.example.banksys.Api.ApiException;
import com.example.banksys.DTO.CustomerDTO;
import com.example.banksys.Model.Customer;
import com.example.banksys.Model.User;
import com.example.banksys.Repository.CustomerRepository;
import com.example.banksys.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //GET
    public List<Customer> findAllCustomers() {

        return (List<Customer>) customerRepository.findAll();
    }
//ADD
    public void registerCustomer(CustomerDTO customerDTO) {
        User user = new User();
        user.setUsername(customerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole(customerDTO.getRole());

        Customer customer = new Customer();
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setUser(user);

        user.setCustomer(customer);

        userRepository.save(user);
    }
    //update
    public void updateCustomer(Integer customerId,CustomerDTO customerDTO) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        User existingUser = customer.getUser();
        //customer Fields
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        //User Fields
        existingUser.setName(customerDTO.getName());
        existingUser.setEmail(customerDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        // Save the updated User and Customer
        customerRepository.save(customer);
        userRepository.save(existingUser);
    }
    //DELETE
    public void deleteCustomer(Integer id,Integer customerId) {
        User user = userRepository.findUserById(id);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        if (user == null){
            throw new ApiException("user not found");
        }
        userRepository.delete(user);
        customerRepository.delete(customer);
    }
    // Get details of the currently authenticated customer
    public Customer getCustomerDetails(Integer userId) {
        customerRepository.findCustomerById(userId);
        if (!customerRepository.existsById(userId)) {
            throw new ApiException("customer not found");
        }
        return customerRepository.findById(userId).get();
    }
}
