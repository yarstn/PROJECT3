package com.example.banksys.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "Account number must follow the format XXXX-XXXX-XXXX-XXXX")
    @Column(columnDefinition = "varchar(19) not null unique")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0", message = "Balance must be a non-negative decimal number")
    @Column(columnDefinition = "decimal(19,2) not null")
    private double balance;


    private Boolean isActive;
    private Boolean isBlocked= false;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}
