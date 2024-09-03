package com.example.banksys.Service;

import com.example.banksys.Api.ApiException;
import com.example.banksys.Model.User;
import com.example.banksys.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
    //add
    public void Register(User user) {
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        userRepository.save(user);
    }



    //update
    public void updateUser(Integer id,User user) {
        User user1 = userRepository.findUserById(id);
        if (user1 == null){
            throw new ApiException("User not found");
        }
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user1.setPassword(hash);
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        userRepository.save(user1);
    }
    //delete
    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("User not found");
        }
        userRepository.deleteById(id);
    }
}
