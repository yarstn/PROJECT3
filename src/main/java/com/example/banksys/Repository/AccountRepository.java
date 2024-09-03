package com.example.banksys.Repository;

import com.example.banksys.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface  AccountRepository extends JpaRepository<Account, Integer> {
    Account findAllById(Integer id);
    List<Account> findByCustomerUserId(Integer userId);
    Account findAccountById(Integer id);

}
