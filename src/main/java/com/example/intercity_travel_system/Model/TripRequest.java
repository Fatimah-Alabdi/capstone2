package com.example.intercity_travel_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer userId;

@NotEmpty(message = "departure Station cannot be empty")
@Column(columnDefinition = "varchar(100) not null")
    private String departureStation;

    @NotEmpty(message = "arrival Station cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String arrivalStation;
    @NotNull(message = "departure Time cannot be null")
    @Column(columnDefinition = "date not null")
    private Timestamp departureTime;
  @NotNull(message = "arrival Time cannot be null")
  @Column(columnDefinition = "date not null")
   private Timestamp arrivalTime;
    @Positive(message = "number of seats must be positive number")
    @NotNull(message = "number of Seats cannot be null")
    @Column(columnDefinition = "int not null")
    private int numberOfSeats;
    @NotEmpty(message = "status cannot be empty")
    @Pattern(regexp = "fulfilled|not fulfilled", message = "status must be either 'not fulfilled' or 'fulfilled' only")
    @Check(constraints = "status IN ('fulfilled', 'not fulfilled')")

    @Column(columnDefinition = " varchar(30) not null")
    private String status="not fulfilled";

}
