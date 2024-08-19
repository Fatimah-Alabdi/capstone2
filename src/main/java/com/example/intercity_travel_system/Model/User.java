package com.example.intercity_travel_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "username cannot be empty")
    @Size(min = 5,message = "username must be more than 5 character")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
@Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = "email cannot be empty")
    @Email
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @Pattern(regexp="(^05\\d{8}$)",message = "phone number must be 10 numbers and start with 05")
    @NotEmpty(message = "phone cannot be empty")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phone;
    @NotNull(message = "balance cannot be null")
    @Positive
    @Column(columnDefinition = "int not null")
    private int balance;
    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "passenger|driver", message = "role must be either 'Passenger' or 'Driver' only")
    @Column(columnDefinition = "varchar(10) not null")
    @Check(constraints = "role IN ('passenger', 'driver')")
    private String role;

}
