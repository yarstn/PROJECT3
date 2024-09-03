package com.example.banksys.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 5, message = "Username must be longer than 4 characters")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;


//    @Size(min = 6, message = "password must be longer than 5 characters")
////    @NotEmpty(message = "password cannot be null or empty")
//    @Column(columnDefinition = "varchar(30) not null ")
    private String password;

    @NotEmpty(message = "name cannot be null or empty")
    @Size(min = 3, message = "name must be longer than 2 characters")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name;

    @Email
    @NotEmpty(message = "email cannot be null or empty")
    @Column(columnDefinition = "varchar(20) not null unique ")
    private String email;

//    @NotEmpty(message = "role cannot be null or empty")
//    @Check(constraints = "role IN ('CUSTOMER','EMPLOYEE')")
//    @Column(columnDefinition = "varchar(10) not null  ")
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Employee employee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
