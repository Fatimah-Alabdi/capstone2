package com.example.intercity_travel_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VehicleOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer userId;
    @NotEmpty(message = "departure Station cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String departureLocation;
    @NotEmpty(message = "arrival Station cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String destinationLocation;
    @NotNull(message = "departure Time cannot be null")
    @Column(columnDefinition = "date not null")
    private Timestamp departureDateTime;
    @NotNull(message = "arrival Time cannot be null")
    @Column(columnDefinition = "date not null")
    private Timestamp arrivalTime;
    @Positive(message = "number of seats must be positive number")
    @NotNull(message = "number of Seats cannot be null")
    @Column(columnDefinition = "int not null")
    private int  availableSeats;
    @NotNull(message = "price cannot be null")
    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private int  pricePerSeat;
}
