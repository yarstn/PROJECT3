package com.example.banksys.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "position cannot be null or empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String position;

    @NotNull(message = "SALARY cannot be null or empty")
   @Min(1)
    @Column(columnDefinition = "int not null ")
    private int salary;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @MapsId
    private User user;
}
