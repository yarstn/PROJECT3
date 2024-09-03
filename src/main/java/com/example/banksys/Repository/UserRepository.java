package com.example.banksys.Repository;

import com.example.banksys.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
    User findUserByUsername(String username);
}
