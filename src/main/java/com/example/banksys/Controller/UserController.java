package com.example.banksys.Controller;

import com.example.banksys.DTO.CustomerDTO;
import com.example.banksys.DTO.EmployeeRegistrationDTO;
import com.example.banksys.Model.User;
import com.example.banksys.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController //json
public class UserController {
    private final UserService userService;
    @GetMapping("/get")
    public ResponseEntity getAll(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(userService.findAll());
    }
    @PostMapping("/add")
    public ResponseEntity register(@Valid @RequestBody User user) {
        userService.Register(user);
        return ResponseEntity.status(200).body("user registered successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id,@Valid @RequestBody User user) {
        userService.updateUser(id,user);
        return ResponseEntity.status(200).body("user updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("user deleted successfully");
    }


}
