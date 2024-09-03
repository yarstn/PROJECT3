package com.example.banksys.Repository;

import com.example.banksys.Model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);
}
