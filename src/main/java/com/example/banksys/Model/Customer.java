package com.example.banksys.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "phoneNumber cannot be null or empty")
    @Column(columnDefinition = "varchar(20) not null ")
    @Pattern(regexp = "^(\\+9665|05|5)([0-9]{8})$", message = "Invalid phone number format")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @MapsId
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts;

}